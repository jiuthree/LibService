package com.example.library.pro.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 异常响应类，当发生异常时向前端返回此类
 * @author He Peng
 * @version 1.0
 * @date 2022/10/27 10:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    /**
     * 业务错误码
     */
    private Integer errorCode;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 错误原因，用于后端定位错误位置
     */
    private String reason;



    public ExceptionResponse(String errorMessage, Exception e) {

        errorMessage = e.getMessage();
        reason = e.getMessage();
    }



}
