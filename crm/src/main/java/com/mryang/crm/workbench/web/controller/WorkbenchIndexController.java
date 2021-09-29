package com.mryang.crm.workbench.web.controller;

import com.mryang.crm.settings.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName IndexController.java
 * @Description TODO
 * @createTime 2021年09月25日 15:46:00
 */
@Controller
@RequestMapping("/workbench")
public class WorkbenchIndexController {

    /**
     * 跳转到工作台 首页
     * @return
     */
    @RequestMapping("/toIndex.do")
    public String toIndex(){

        return "/workbench/index";
    }

    /**
     * 工作台-首页显示
     * @return
     */
    @RequestMapping("/toWorkbench.do")
    public String toWorkbench() {
        return "/workbench/main/index";
    }

}
