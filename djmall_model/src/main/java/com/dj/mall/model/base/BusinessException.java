/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_model
 * 类名：BusinessException
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.model.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chengf
 * @date 2020/4/1 23:23
 * 自定义-业务处理异常类
 */
@Data
public class BusinessException extends RuntimeException implements Serializable {

    private String errorMsg;

    private Integer errorCode = -1;

    public BusinessException() {}

    public BusinessException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public BusinessException(Integer errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusinessException(Integer errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

}
