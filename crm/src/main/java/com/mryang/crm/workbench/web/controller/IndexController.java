package com.mryang.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName IndexController.java
 * @Description TODO
 * @createTime 2021年09月25日 15:46:00
 */
@Controller
@RequestMapping("/workbench")
public class IndexController {

    /**
     * 跳转到工作台 首页
     * @return
     */
    @RequestMapping("/toIndex.do")
    public String toIndex(){
        return "/workbench/index";
    }

}
