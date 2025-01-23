package com.yyz.comp390.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyz.comp390.entity.User;
import com.yyz.comp390.entity.dto.UserDTO;
import com.yyz.comp390.entity.dto.UserEditDTO;
import com.yyz.comp390.entity.dto.UserListDTO;
import com.yyz.comp390.entity.vo.UserListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AdminMapper extends BaseMapper<User> {

    @Select("select count(*) from user where username = #{username}")
    int getUserByUsername(String username);

    List<UserListVO> getUsers(@Param("userDTO") UserListDTO userDTO);

    @Update("update user set username = #{userEditDTO.username}, password = #{userEditDTO.password}, role = #{userEditDTO.role} where id = #{userEditDTO.id}")
    void editUser(@Param("userEditDTO") UserEditDTO userEditDTO);

    @Select("select id, username, password, role from user where id = #{id}")
    UserDTO getUserById(Long id);

    void deleteUsers(List<Long> ids);

    List<Long> getUserIdsByUserName(String username);

}
