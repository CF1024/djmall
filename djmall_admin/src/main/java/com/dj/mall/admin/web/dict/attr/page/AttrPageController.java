package com.dj.mall.admin.web.dict.attr.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chengf
 * @date 2020/6/27 20:56
 * 商品属性
 */
@Controller
@RequestMapping("/dict/attr/")
public class AttrPageController {

    @GetMapping("toShow")
    public String toShow() {
        return "dict/attr/show";
    }
}
