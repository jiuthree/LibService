package com.example.library.pro.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookVo {

    @ApiModelProperty(value = "作者id")
    Long authorId;

    @ApiModelProperty(value = "ISBN号码")
    String ISBN;

    @ApiModelProperty(value = "文档标题")
    String title;

    @ApiModelProperty(value = "发布者id")
    Long publisherId;

    @ApiModelProperty(value = "发布者日期")
    LocalDateTime publicationDate;
}
