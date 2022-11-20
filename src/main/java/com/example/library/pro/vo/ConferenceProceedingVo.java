package com.example.library.pro.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceProceedingVo {

    @ApiModelProperty(value = "会议纪要日期")
    LocalDateTime date;

    @ApiModelProperty(value = "编辑人")
    String editor;

    @ApiModelProperty(value = "文档标题")
    String title;

    @ApiModelProperty(value = "发布者id")
    Long publisherId;

    @ApiModelProperty(value = "发布者日期")
    LocalDateTime publicationDate;

    @ApiModelProperty(value = "会议地点")
    String location;
}
