package com.yyz.comp390.service.impl;

import com.yyz.comp390.context.BaseContext;
import com.yyz.comp390.entity.Algorithm;
import com.yyz.comp390.entity.ApiResult;
import com.yyz.comp390.entity.dto.EditAlgorithmDTO;
import com.yyz.comp390.entity.dto.GetAlgorithmDTO;
import com.yyz.comp390.entity.vo.GetAlgorithmVO;
import com.yyz.comp390.mapper.AdminMapper;
import com.yyz.comp390.mapper.AlgorithmMapper;
import com.yyz.comp390.service.AlgorithmService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlgorithmServiceImpl implements AlgorithmService {

    @Resource
    AdminMapper adminMapper;

    @Resource
    AlgorithmMapper algorithmMapper;

    @Override
    public List<GetAlgorithmVO> getAlgorithms(GetAlgorithmDTO getAlgorithmDTO) {

        List<Long> idList = adminMapper.getUserIdsByUserName(getAlgorithmDTO.getCreator());
        if(idList.isEmpty()){
            idList = null;
        }
        List<GetAlgorithmVO> getAlgorithmVOs = algorithmMapper.getAlgorithms(idList, getAlgorithmDTO);
        return getAlgorithmVOs;
    }

    @Override
    public GetAlgorithmVO getAlgorithmById(Long id) {
        return algorithmMapper.getAlgorithmById(id);
    }

    @Override
    public ApiResult addAlgorithm(GetAlgorithmDTO algorithmDTO) {
        Algorithm algorithm = new Algorithm();
        algorithm.setName(algorithmDTO.getName());
        algorithm.setDescription(algorithmDTO.getDescription());
        algorithm.setFunctionName(algorithmDTO.getFunctionName());
        algorithm.setCreateId(BaseContext.getCurrentId());
        algorithm.setCreateTime(LocalDateTime.now());
        algorithm.setStatus(algorithmDTO.getStatus());
        algorithmMapper.insert(algorithm);
        return ApiResult.success();
    }

    @Override
    public ApiResult editAlgorithm(EditAlgorithmDTO editAlgorithmDTO) {
        algorithmMapper.editAlgorithm(editAlgorithmDTO);
        return ApiResult.success();
    }

    @Override
    public void deleteAlgorithms(List<Long> ids) {
        algorithmMapper.deleteBatchIds(ids);
    }
}
