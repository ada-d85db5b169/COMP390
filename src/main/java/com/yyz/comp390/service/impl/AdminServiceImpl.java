package com.yyz.comp390.service.impl;

import com.yyz.comp390.entity.User;
import com.yyz.comp390.entity.dto.UserDTO;
import com.yyz.comp390.entity.dto.UserEditDTO;
import com.yyz.comp390.entity.dto.UserListDTO;
import com.yyz.comp390.entity.vo.UserListVO;
import com.yyz.comp390.exception.CreateUserException;
import com.yyz.comp390.mapper.AdminMapper;
import com.yyz.comp390.service.AdminService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Resource
    AdminMapper adminMapper;

    @Override
    public void createUser(UserDTO userDTO) {

        if(adminMapper.getUserByUsername(userDTO.getUsername()) > 0) {
            throw new CreateUserException("Username already exists");
        }

        if(!userDTO.getRole().equals("CURATOR") && !userDTO.getRole().equals("USER")) {
            throw new CreateUserException("Unknown User Type!");
        }

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setPassword(DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes()));
        user.setDelFlag("NOT_DELETE");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        adminMapper.insert(user);
    }

    @Override
    public List<UserListVO> getAllUsers(UserListDTO userDTO) {
        return adminMapper.getUsers(userDTO);
    }

    @Override
    public void editUser(UserEditDTO userEditDTO) {
        log.info(userEditDTO.toString());
        userEditDTO.setPassword(DigestUtils.md5DigestAsHex(userEditDTO.getPassword().getBytes()));
        adminMapper.editUser(userEditDTO);
    }

    @Override
    public UserDTO getUserById(Long id) {
        return adminMapper.getUserById(id);
    }

    @Override
    public void deleteUsers(List<Long> id) {
        adminMapper.deleteUsers(id);
    }


}
