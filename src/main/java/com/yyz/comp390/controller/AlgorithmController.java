package com.yyz.comp390.controller;

import com.yyz.comp390.entity.ApiResult;
import com.yyz.comp390.entity.dto.EditAlgorithmDTO;
import com.yyz.comp390.entity.dto.GetAlgorithmDTO;
import com.yyz.comp390.entity.vo.GetAlgorithmVO;
import com.yyz.comp390.service.AlgorithmService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/algorithm")
public class AlgorithmController {

    @Resource
    private AlgorithmService algorithmService;

    @PostMapping("/getAlgorithms")
    public ApiResult<List<GetAlgorithmVO>> getAlgorithms(@RequestBody GetAlgorithmDTO getAlgorithmDTO) {
        return ApiResult.success(algorithmService.getAlgorithms(getAlgorithmDTO));
    }

    @GetMapping("/getAlgorithm/{id}")
    public ApiResult<GetAlgorithmVO> getAlgorithmById(@PathVariable Long id) {
        return ApiResult.success(algorithmService.getAlgorithmById(id));
    }

    @PostMapping("/addAlgorithm")
    public ApiResult addAlgorithm(@RequestBody GetAlgorithmDTO algorithmDTO) {
        return algorithmService.addAlgorithm(algorithmDTO);
    }

    @PostMapping("/editAlgorithm")
    public ApiResult editAlgorithm(@RequestBody EditAlgorithmDTO editAlgorithmDTO) {
        return algorithmService.editAlgorithm(editAlgorithmDTO);
    }

    @PostMapping("/deleteAlgorithms")
    public ApiResult deleteAlgorithms(@RequestBody List<Long> ids) {
        algorithmService.deleteAlgorithms(ids);
        return ApiResult.success();
    }
}
