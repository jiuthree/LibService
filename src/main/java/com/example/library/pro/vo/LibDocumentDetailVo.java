package com.example.library.pro.vo;

import com.example.library.pro.module.LibDocuments;
import com.example.library.pro.module.ReserveAndBorrowList;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibDocumentDetailVo {

    @ApiModelProperty(value = "文档copy的馆藏情况")
    LibDocuments libDocument;

    @ApiModelProperty(value = "文档copy的借阅情况，如果为null，说明目前未被借阅")
    ReserveAndBorrowList reserveAndBorrowList;
}
