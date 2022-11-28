package com.example.library.pro.vo;

import com.example.library.pro.constants.DocumentStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveVo {

    @ApiModelProperty(value = "读者id")
    Long readerId;

    @ApiModelProperty(value = "文档id")
    Long documentId;

    @ApiModelProperty(value = "图书馆id")
    Long libId;

    @ApiModelProperty(value = "文档副本编号，预定书籍的时候前端不用传，后端会返回")
    Long number;

    @ApiModelProperty(value = "文档状态，前端不用传，后端会返回")
    DocumentStatus status;

    @ApiModelProperty(value = "借书日期，前端不用传，后端会返回")
    LocalDateTime bDateTime;

    @ApiModelProperty(value = "还书日期，前端不用传，后端会返回")
    LocalDateTime rDateTime;

    @ApiModelProperty(value = "预定日期，前端不用传，后端会返回")
    LocalDateTime reserveDate;
}
