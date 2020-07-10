/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：CustomExceptionHandler
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.platform.config;

import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.ResultModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author chengf
 * @date 2020/7/10 17:15
 * 自定义异常处理器
 */
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * 业务异常处理
     * @param ex BusinessException
     * @return ResultModel
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public ResultModel<Object> businessExceptionHandler(BusinessException ex) {
        ex.printStackTrace();
        return new ResultModel<>().error(ex.getErrorCode(), ex.getErrorMsg());
    }

    /**
     * 参数异常处理
     * @param ex IllegalArgumentException
     * @return ResultModel
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResultModel<Object> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        ex.printStackTrace();
        return new ResultModel<>().error(ex.getMessage());
    }

    /**
     * 参数异常处理
     * @param ex IllegalStateException
     * @return ResultModel
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(IllegalStateException.class)
    public ResultModel<Object> illegalStateExceptionExceptionHandler(IllegalStateException ex) {
        ex.printStackTrace();
        return new ResultModel<>().error(ex.getMessage());
    }

    /**
     * 未知异常处理
     * @param ex Exception
     * @return ResultModel
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    public ResultModel<Object> exceptionHandler(Exception ex) {
        ex.printStackTrace();
        return new ResultModel<>().error(-2, "服务器在开小差，请稍后再试");
    }

}
