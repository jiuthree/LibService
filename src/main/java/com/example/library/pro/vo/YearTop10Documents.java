package com.example.library.pro.vo;

import com.example.library.pro.module.Document;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearTop10Documents {

    @ApiModelProperty(value = "文档信息")
    Document document;

    @ApiModelProperty(value = "借阅的次数")
    Long borrowedNums;

}
