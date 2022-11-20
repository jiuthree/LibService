package com.example.library.pro.module;

import com.example.library.pro.constants.DocumentStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ReserveAndBorrowList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "id")
    Long id;

    @ApiModelProperty(value = "读者id")
    Long readerId;

    @ApiModelProperty(value = "文档id")
    Long documentId;

    @ApiModelProperty(value = "文档编号")
    Long number;

    @ApiModelProperty(value = "文档状态")
    DocumentStatus status;

    @ApiModelProperty(value = "借书日期")
    LocalDateTime bDateTime;

    @ApiModelProperty(value = "还书日期")
    LocalDateTime rDateTime;

    @ApiModelProperty(value = "预定日期")
    LocalDateTime reserveDate;
}
