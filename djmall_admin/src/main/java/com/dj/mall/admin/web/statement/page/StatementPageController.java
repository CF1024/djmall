/*
 * 作者：CF
 * 日期：2020-08-19 16:03
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：StatementController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.web.statement.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statement/")
public class StatementPageController {
    @GetMapping("toShow")
    public String toShow() {
        return "statement/show";
    }
}
