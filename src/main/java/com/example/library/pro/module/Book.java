package com.example.library.pro.module;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
    @GenericGenerator(name = "myid", strategy = "com.example.library.pro.util.ManulInsertGenerator")
    @ApiModelProperty(value = "id")
    Long id;

    @ApiModelProperty(value = "作者id")
    Long authorId;

    @ApiModelProperty(value = "ISBN号码")
    String ISBN;
}
