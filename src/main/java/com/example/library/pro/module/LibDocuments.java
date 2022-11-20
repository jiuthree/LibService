package com.example.library.pro.module;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class LibDocuments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "id")
    Long id;

    @ApiModelProperty(value = "图书馆id")
    Long libId;

    @ApiModelProperty(value = "文档id")
    Long documentId;

    @ApiModelProperty(value = "此文档在此图书馆的总数量")
    Long totalNumber;

    @ApiModelProperty(value = "文档副本编号")
    Long number;
}
