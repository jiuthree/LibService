package com.example.library.pro.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentContent {

    @ApiModelProperty(value = "书籍的content，部分在Document已经显示的字段就不重复显示了")
    BookVo bookVo;

    @ApiModelProperty(value = "杂志的content，部分在Document已经显示的字段就不重复显示了")
    JournalVo journalVo;

    @ApiModelProperty(value = "会议纪要的content，部分在Document已经显示的字段就不重复显示了")
    ConferenceProceedingVo conferenceProceedingVo;
}
