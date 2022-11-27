package com.example.library.pro.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VolumeVo {

    @ApiModelProperty(value = "编号")
    String number;

    @ApiModelProperty(value = "编辑")
    String editor;
}
