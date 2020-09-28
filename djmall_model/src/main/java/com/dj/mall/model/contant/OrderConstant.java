/*
 * 作者：CF
 * 日期：2020-07-30 21:25
 * 项目：djmall
 * 模块：djmall_model
 * 类名：OrderConstant
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.model.contant;

public interface OrderConstant {
    /**
     * 订单前缀
     */
    String ORDER_NO_PREFIX = "DJ";

    /**
     * 0
     */
    Integer ZERO = 0;

    /**
     * 明细表状态：未评论 0
     */
    Integer NOT_COMMENT = 0;

    /**
     * 明细表状态：已评论
     */
    Integer IS_COMMENT = 1;
    /**
     * 每页条数
     */
    Integer PAGE_SIZE = 4;
    /**
     * token
     */
    String TOKEN = "TOKEN";
    /**
     * 提醒时间 买家6个小时内只能提醒一次
     */
    Integer REMIND_TIME = 6;
    /**
     * 未提醒状态
     */
    Integer NOT_REMIND_STATUS = 1;
    /**
     * 负一
     */
    Integer MINUS_ONE = -1;
}
