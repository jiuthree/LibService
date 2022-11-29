package com.example.library.pro.vo;

import com.example.library.pro.module.ReserveAndBorrowList;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnVo {

    @ApiModelProperty(value = "文档借阅信息")
    ReserveAndBorrowList reserveAndBorrowList;

    @ApiModelProperty(value = "违约还书所欠的费用")
    Long cost;
}
