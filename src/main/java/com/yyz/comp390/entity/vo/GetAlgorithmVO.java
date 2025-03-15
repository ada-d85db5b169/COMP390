package com.yyz.comp390.entity.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class GetAlgorithmVO {

    private Long id;

    private String name;

    private String description;

    private String className;

    private String functionName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private String creator;

    private String status;

}
