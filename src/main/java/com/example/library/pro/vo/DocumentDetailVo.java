package com.example.library.pro.vo;

import com.example.library.pro.constants.DocumentType;
import com.example.library.pro.module.Publisher;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDetailVo {

    @ApiModelProperty(value = "id")
    Long id;

    @ApiModelProperty(value = "文档标题")
    String title;

    @ApiModelProperty(value = "发布者")
    Publisher publisher;

    @ApiModelProperty(value = "发布者日期")
    LocalDateTime publicationDate;

    @ApiModelProperty(value = "文档类型")
    DocumentType type;

    @ApiModelProperty(value = "各个图书馆的馆藏记录")
    List<LibDocumentVo> libDocumentVos;

    @ApiModelProperty(value = "document的具体内容，根据document类型会有不同")
    DocumentContent documentContent;

}
