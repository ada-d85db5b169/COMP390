package com.yyz.comp390.service;

import com.yyz.comp390.entity.User;
import com.yyz.comp390.entity.dto.LoginDTO;
import com.yyz.comp390.entity.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User login(LoginDTO loginDTO);
}
