package com.example.library.pro.vo;

import com.example.library.pro.module.Document;
import com.example.library.pro.module.Library;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryTop10Documents {
    @ApiModelProperty(value = "图书馆信息")
    Library library;

    @ApiModelProperty(value = "排名靠前的文档信息")
    List<Document> topDocuments;
}
