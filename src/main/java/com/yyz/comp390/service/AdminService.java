package com.yyz.comp390.service;

import com.yyz.comp390.entity.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    void createUser(UserDTO userDTO);
}
