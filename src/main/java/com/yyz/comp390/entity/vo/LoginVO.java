package com.yyz.comp390.entity.vo;

import lombok.Data;

@Data
public class LoginVO {

    private Long id;
    private String username;
    private String token;
    private String permission;

}
