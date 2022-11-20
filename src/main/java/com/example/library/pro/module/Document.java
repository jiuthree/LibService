package com.example.library.pro.module;

import com.example.library.pro.constants.DocumentType;
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
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "id")
    Long id;

    @ApiModelProperty(value = "文档标题")
    String title;

    @ApiModelProperty(value = "发布者id")
    Long publisherId;

    @ApiModelProperty(value = "发布者日期")
    LocalDateTime publicationDate;

    @ApiModelProperty(value = "文档类型")
    DocumentType type;
}
