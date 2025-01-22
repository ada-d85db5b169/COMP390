package com.yyz.comp390.service;

import com.yyz.comp390.entity.dto.UserDTO;
import com.yyz.comp390.entity.dto.UserEditDTO;
import com.yyz.comp390.entity.dto.UserListDTO;
import com.yyz.comp390.entity.vo.UserListVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {
    void createUser(UserDTO userDTO);

    List<UserListVO> getAllUsers(UserListDTO userDTO);

    void editUser(UserEditDTO userEditDTO);

    UserDTO getUserById(Long id);

    void deleteUsers(List<Long> id);
}
