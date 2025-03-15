package com.yyz.comp390.entity;

import lombok.Data;

@Data
public class Subset {
    private String columnName;
    private Double min;
    private Double max;
    private Double epsilon;
}
