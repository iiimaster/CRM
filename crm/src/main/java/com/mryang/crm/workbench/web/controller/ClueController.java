package com.mryang.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName ClueController.java
 * @Description TODO 线索模块
 * @createTime 2021年10月19日 21:04:00
 */
@Controller
@RequestMapping("/workbench/clue")
public class ClueController {

    /**
     * 线索页面-首页面展示
     * @return
     */
    @RequestMapping("/toIndex.do")
    public String toIndex(){
        return "/workbench/clue/index";
    }




}
