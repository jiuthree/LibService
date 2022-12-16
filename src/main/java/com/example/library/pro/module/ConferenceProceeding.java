package com.example.library.pro.module;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ConferenceProceeding {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
    @GenericGenerator(name = "myid", strategy = "com.example.library.pro.util.ManulInsertGenerator")
    @ApiModelProperty(value = "id")
    Long id;

    @ApiModelProperty(value = "会议纪要日期")
    LocalDateTime date;

    @ApiModelProperty(value = "编辑人")
    String editor;

    @ApiModelProperty(value = "会议地点")
    String location;
}
