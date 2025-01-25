package com.yyz.comp390.controller;

import com.alibaba.fastjson2.JSONObject;
import com.yyz.comp390.entity.ApiResult;
import com.yyz.comp390.entity.dto.QueryDTO;
import com.yyz.comp390.service.QueryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/query")
public class QueryController {

    @Resource
    QueryService queryService;

    @PostMapping("/query")
    public ApiResult<JSONObject> query(@RequestBody QueryDTO queryDTO) {
        return ApiResult.success(queryService.query(queryDTO));
    }

}
