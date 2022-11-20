package com.example.library.pro.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @ApiModelProperty(value = "电话号码")
    String phoneNumber;

    @ApiModelProperty(value = "密码")
    String password;

}
