package com.yyz.comp390.entity.vo;

import com.yyz.comp390.entity.dto.GetFileDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetFileVO extends GetFileDTO {

    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private Integer privacyBudget;

    private Double epsilon;
}
