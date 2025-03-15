package com.yyz.comp390.service;

import com.alibaba.fastjson2.JSONObject;
import com.yyz.comp390.entity.dto.QueryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QueryService {

    JSONObject query(QueryDTO queryDTO);

    List<String> getFileColumns(Long id);

    Double getBudget(Long id);
}
