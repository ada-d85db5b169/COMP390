package com.yyz.comp390.service.impl;

import com.yyz.comp390.entity.User;
import com.yyz.comp390.entity.dto.UserDTO;
import com.yyz.comp390.exception.LoginFailedException;
import com.yyz.comp390.mapper.UserMapper;
import com.yyz.comp390.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public User login(UserDTO userDTO) {

        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        User user = userMapper.getByUserName(username);

        if(user==null){
            throw new LoginFailedException("User not found! Please check username and password!");
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!password.equals(user.getPassword())) {
            throw new LoginFailedException("Username or password is incorrect!");
        }
        return user;
    }
}
