package com.yyz.comp390.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyz.comp390.entity.Algorithm;
import com.yyz.comp390.entity.dto.EditAlgorithmDTO;
import com.yyz.comp390.entity.dto.GetAlgorithmDTO;
import com.yyz.comp390.entity.vo.GetAlgorithmVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AlgorithmMapper extends BaseMapper<Algorithm> {
    List<GetAlgorithmVO> getAlgorithms(@Param("idList") List<Long> idList, @Param("getAlgorithmDTO") GetAlgorithmDTO getAlgorithmDTO);

    @Select("select name, description, function_name, status from algorithm")
    GetAlgorithmVO getAlgorithmById(Long id);

    @Update("update algorithm set name = #{editAlgorithmDTO.name}, description = #{editAlgorithmDTO.description}," +
            "function_name = #{editAlgorithmDTO.functionName}, status = #{editAlgorithmDTO.status} " +
            "where id = #{editAlgorithmDTO.id}")
    void editAlgorithm(@Param("editAlgorithmDTO") EditAlgorithmDTO editAlgorithmDTO);
}
