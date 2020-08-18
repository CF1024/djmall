/*
 * 作者：CF
 * 日期：2020-08-03 17:02
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：OrderPageController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.web.order.page;

import com.dj.mall.model.contant.PermissionsCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order/")
public class OrderPageController {

    /**
     * 去订单展示
     * @return 订单展示
     */
    @GetMapping("toShow")
    @RequiresPermissions(value = PermissionsCode.ORDER_MANAGE)
    public String toShow() {
        return "order/show";
    }
}
