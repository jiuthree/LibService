package com.example.library.pro.module;

import com.example.library.pro.constants.ReaderType;
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
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "id")
    Long id;

    @ApiModelProperty(value = "违约还书所欠的费用")
    Long cost;

    @ApiModelProperty(value = "居住地址")
    String address;

    @ApiModelProperty(value = "姓名")
    String name;

    @Column(unique = true)
    @ApiModelProperty(value = "电话号码")
    String phoneNumber;

    @ApiModelProperty(value = "密码")
    String password;

    @ApiModelProperty(value = "读者类型")
    ReaderType type;
}
