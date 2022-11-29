package com.example.library.pro.vo;

import com.example.library.pro.module.Document;
import com.example.library.pro.module.ReserveAndBorrowList;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveAndBorrowDetailVo {

    @ApiModelProperty(value = "文档的具体内容")
    Document document;

    @ApiModelProperty(value = "文档的借阅信息")
    ReserveAndBorrowList reserveAndBorrowList;

}
