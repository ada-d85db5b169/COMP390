package com.yyz.comp390.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.yyz.comp390.controller.FileController;
import com.yyz.comp390.entity.File;
import com.yyz.comp390.entity.dto.PrivacyBudgetKV;
import com.yyz.comp390.entity.dto.QueryDTO;
import com.yyz.comp390.entity.dto.QueryNameDTO;
import com.yyz.comp390.exception.FileException;
import com.yyz.comp390.exception.QueryException;
import com.yyz.comp390.mapper.AlgorithmMapper;
import com.yyz.comp390.mapper.FileMapper;
import com.yyz.comp390.service.QueryService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QueryServiceImpl implements QueryService {

    @Resource
    FileMapper fileMapper;

    @Resource
    AlgorithmMapper algorithmMapper;

    private final RestTemplate restTemplate;

    private final Map<Long, Integer> privacyBudget = new HashMap<>();

    public static String pythonURL = "http://127.0.0.1:5000/analyze";

    public QueryServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @PostConstruct
    public void initializePrivacyBudgetMap(){
        List<PrivacyBudgetKV> kv = fileMapper.getAllPrivacyBudget();
        for(PrivacyBudgetKV privacyBudgetKV : kv){
            privacyBudget.put(privacyBudgetKV.getId(), privacyBudgetKV.getPrivacyBudget());
        }
    }

    private void handlePrivacyBudget(Long id){
        if(!privacyBudget.containsKey(id)){
            throw new QueryException("File no longer available");
        }
        privacyBudget.put(id, privacyBudget.get(id) - 1);
        if(privacyBudget.get(id) == 0){
            privacyBudget.remove(id);
            fileMapper.setFilePermissionNoById(id);
        }
        fileMapper.deductPrivacyBudgetById(id);
    }

    @Override
    public JSONObject query(QueryDTO queryDTO) {

        File file = fileMapper.getFullFileById(queryDTO.getFileId());

        if(file.getPrivacyBudget() <= 0){
            throw new FileException("This file is no longer available!");
        }

        String filePath = FileController.downloadPath + "\\" + file.getAlias();
        QueryNameDTO nameDTO = algorithmMapper.getQueryNamesById(queryDTO.getAlgorithmId());
        String className = nameDTO.getClassName();
        String functionName = nameDTO.getFunctionName();
        Map<String, Object> params = new HashMap<>();
        params.put("epsilon", file.getEpsilon());
        params.put("delta", file.getDelta());

        Map<String, Object> request = new HashMap<>();
        request.put("className", className);
        request.put("filePath", filePath);
        request.put("parameter", params);
        request.put("functionName", functionName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(pythonURL, entity, String.class);
        JSONObject jsonObject = JSON.parseObject(response.getBody());

        if(response.getStatusCode().value() == 200){
            handlePrivacyBudget(file.getId());
            return jsonObject;
        } else if (response.getStatusCode().value() == 400){
            throw new QueryException(response.getBody());
        } else {
            throw new QueryException("Failed to analyze file: " + file.getFilename());
        }
    }
}
