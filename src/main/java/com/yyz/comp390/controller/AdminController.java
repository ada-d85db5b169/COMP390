package com.yyz.comp390.controller;

import com.yyz.comp390.entity.ApiResult;
import com.yyz.comp390.entity.dto.UserDTO;
import com.yyz.comp390.service.AdminService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Resource
    AdminService adminService;

    @PostMapping("/createUser")
    public ApiResult createUser(@RequestBody UserDTO userDTO) {
        adminService.createUser(userDTO);
        return ApiResult.success();
    }

}
