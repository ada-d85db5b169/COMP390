package com.yyz.comp390.controller;

import com.yyz.comp390.entity.ApiResult;
import com.yyz.comp390.entity.dto.UserDTO;
import com.yyz.comp390.entity.dto.UserEditDTO;
import com.yyz.comp390.entity.dto.UserListDTO;
import com.yyz.comp390.entity.vo.UserListVO;
import com.yyz.comp390.service.AdminService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/getAllUsers")
    public ApiResult<List<UserListVO>> getAllUsers(@RequestBody UserListDTO userListDTO) {
        return ApiResult.success(adminService.getAllUsers(userListDTO));
    }

    @GetMapping("/getUserById/{id}")
    public ApiResult<UserDTO> getUserById(@PathVariable Long id) {
        return ApiResult.success(adminService.getUserById(id));
    }

    @PostMapping("/editUser")
    public ApiResult editUser(@RequestBody UserEditDTO userDTO) {
        adminService.editUser(userDTO);
        return ApiResult.success();
    }

    @PostMapping("/deleteUsers")
    public ApiResult deleteUsers(@RequestBody List<Long> id) {
        adminService.deleteUsers(id);
        return ApiResult.success();
    }
}
