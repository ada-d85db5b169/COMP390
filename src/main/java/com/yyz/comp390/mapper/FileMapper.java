package com.yyz.comp390.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyz.comp390.entity.File;
import com.yyz.comp390.entity.dto.EditFileDTO;
import com.yyz.comp390.entity.dto.GetFileDTO;
import com.yyz.comp390.entity.vo.GetFileVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FileMapper extends BaseMapper<File> {
    List<GetFileVO> getFiles(@Param("idList") List<Integer> idList, @Param("getFileDTO") GetFileDTO getFileDTO);

    @Update("update file set filename = #{editFileDTO.filename}, privacy_budget = #{editFileDTO.privacyBudget}, " +
            "epsilon = #{editFileDTO.epsilon}, permission = #{editFileDTO.permission}, " +
            "privacy_budget = #{editFileDTO.privacyBudget} where id = #{editFileDTO.id}")
    void editFile(@Param("editFileDTO") EditFileDTO editFileDTO);

    List<String> getFileAliasByIds(@Param("ids") List<Long> ids);

    @Select("select filename, epsilon, privacy_budget, permission from file where id = #{id}")
    GetFileVO getFileById(Long id);
}
