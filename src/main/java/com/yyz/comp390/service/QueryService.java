package com.yyz.comp390.service;

import com.alibaba.fastjson2.JSONObject;
import com.yyz.comp390.entity.dto.QueryDTO;
import org.springframework.stereotype.Service;

@Service
public interface QueryService {

    JSONObject query(QueryDTO queryDTO);
}
