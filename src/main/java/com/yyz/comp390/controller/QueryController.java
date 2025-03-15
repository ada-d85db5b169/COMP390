package com.yyz.comp390.controller;

import com.alibaba.fastjson2.JSONObject;
import com.yyz.comp390.entity.ApiResult;
import com.yyz.comp390.entity.dto.QueryDTO;
import com.yyz.comp390.service.QueryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/query")
public class QueryController {

    @Resource
    QueryService queryService;

    @PostMapping("/query")
    public ApiResult<JSONObject> query(@RequestBody QueryDTO queryDTO) {
        return ApiResult.success(queryService.query(queryDTO));
    }

    @GetMapping("/getFileColumns/{id}")
    public ApiResult<List<String>> getFileColumns(@PathVariable Long id) {
        return ApiResult.success(queryService.getFileColumns(id));
    }

    @GetMapping("/getBudget/{id}")
    public ApiResult<Double> getBudget(@PathVariable Long id) {
        return ApiResult.success(queryService.getBudget(id));
    }
}
