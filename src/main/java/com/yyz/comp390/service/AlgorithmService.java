package com.yyz.comp390.service;

import com.yyz.comp390.entity.ApiResult;
import com.yyz.comp390.entity.dto.EditAlgorithmDTO;
import com.yyz.comp390.entity.dto.GetAlgorithmDTO;
import com.yyz.comp390.entity.vo.GetAlgorithmVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AlgorithmService {
    List<GetAlgorithmVO> getAlgorithms(GetAlgorithmDTO getAlgorithmDTO);

    GetAlgorithmVO getAlgorithmById(Long id);

    void addAlgorithm(GetAlgorithmDTO algorithmDTO);

    ApiResult editAlgorithm(EditAlgorithmDTO editAlgorithmDTO);

    void deleteAlgorithms(List<Long> ids);
}
