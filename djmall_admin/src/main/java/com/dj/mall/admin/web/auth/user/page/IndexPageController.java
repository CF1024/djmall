/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：IndexPageController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.web.auth.user.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chengf
 * @date 2020/4/2 15:14
 * 三分天下
 */
@Controller
@RequestMapping("/index/")
public class IndexPageController {
    /**
     * 全局
     * @return
     */
    @RequestMapping("toIndex")
    public String toIndex() {
        return "index/index";
    }

    /**
     * 上
     * @return
     */
    @RequestMapping("toTop")
    public String toTop() {
        return "index/top";
    }

    /**
     * 左
     * @return
     */
    @RequestMapping("toLeft")
    public String toLeft() {
        return "index/left";
    }

    /**
     * 右
     * @return
     */
    @RequestMapping("toRight")
    public String toRight() {
        return "index/right";
    }

    /**
     * 403页面
     * @return
     */
    @RequestMapping("toDisplay")
    public String toDisplay() {
        return "index/403";
    }
}
