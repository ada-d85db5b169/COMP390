package com.yyz.comp390.entity.dto;

import lombok.Data;

@Data
public class EditFileDTO {

    private Long id;

    private String filename;

    private Double epsilon;

    private Double delta;

    private Integer privacyBudget;

    private String permission;

}
