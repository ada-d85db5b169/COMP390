package com.yyz.comp390.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserEditDTO extends UserDTO {

    private Long id;

}
