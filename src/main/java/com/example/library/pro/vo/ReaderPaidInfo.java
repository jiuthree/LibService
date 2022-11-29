package com.example.library.pro.vo;

import com.example.library.pro.module.Reader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaderPaidInfo {

    Reader reader;

    double averageFinePaid;
}
