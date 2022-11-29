package com.example.library.pro.vo;

import com.example.library.pro.module.Reader;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopReader {

    @ApiModelProperty(value = "读者信息")
    Reader reader;

    @ApiModelProperty(value = "借阅的书籍数量")
    Long borrowedDocumentCounts;

}
