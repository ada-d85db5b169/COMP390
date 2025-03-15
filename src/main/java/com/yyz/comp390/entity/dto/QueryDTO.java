package com.yyz.comp390.entity.dto;

import com.yyz.comp390.entity.Subset;
import lombok.Data;
import java.util.List;

@Data
public class QueryDTO {

    private Long fileId;

    private Long algorithmId;

    private List<Filter> filters;

    private List<Subset> subsets;

    // For conditional query or user defined epsilon
    private Double epsilon;

    // For parallel composition
    private String columnName;
}

@Data
class Filter {
    private String columnName;
    private String operator;
    private Double value;
    private String logic;
}

