package com.example.library.pro.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibDocumentVo {

    @ApiModelProperty(value = "id")
    Long id;

    @ApiModelProperty(value = "图书馆id")
    Long libId;

    @ApiModelProperty(value = "图书馆地理位置")
    String libraryLocation;

    @ApiModelProperty(value = "图书馆名称")
    String libraryName;

    @ApiModelProperty(value = "文档id")
    Long documentId;

    @ApiModelProperty(value = "此文档在此图书馆的总数量")
    Long totalNumber;

    @ApiModelProperty(value = "文档副本编号")
    Long number;
}
