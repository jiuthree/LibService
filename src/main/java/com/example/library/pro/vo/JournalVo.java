package com.example.library.pro.vo;

import com.example.library.pro.module.Volume;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalVo {

    @ApiModelProperty(value = "journal的id，前端不用传，后端会返回")
    Long id;

    @ApiModelProperty(value = "文档标题")
    String title;

    @ApiModelProperty(value = "发布者id")
    Long publisherId;

    @ApiModelProperty(value = "发布者日期")
    LocalDateTime publicationDate;

    @ApiModelProperty(value = "杂志名字")
    String name;

    @ApiModelProperty(value = "领域")
    String scope;

    @ApiModelProperty(value = "编辑")
    String editor;

    @ApiModelProperty(value = "volumes的数组")
    List<Volume> volumes;
}
