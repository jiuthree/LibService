package com.example.library.pro.vo;

import com.example.library.pro.constants.ReaderType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReaderVo {

    @ApiModelProperty(value = "居住地址")
    String address;

    @ApiModelProperty(value = "姓名")
    String name;

    @ApiModelProperty(value = "电话号码")
    String phoneNumber;

    @ApiModelProperty(value = "读者类型")
    ReaderType type;

    @ApiModelProperty(value = "读者的卡号，作为读者登录使用")
    Long cardNumber;


}
