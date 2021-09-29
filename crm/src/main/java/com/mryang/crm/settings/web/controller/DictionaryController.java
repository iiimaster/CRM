package com.mryang.crm.settings.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName DictionaryController.java
 * @Description TODO 数据字典表-模块
 * @createTime 2021年09月29日 13:52:00
 */
@Controller
@RequestMapping("/settings/dictionary")
public class DictionaryController {

    /**
     * 跳转 数据字典 首页
     * @return
     */
    @RequestMapping("/toIndex.do")
    public String toIndex(){
        return "/settings/dictionary/index";
    }


    @RequestMapping("/toTypeIndex.do")
    public String toTypeIndex(){
        return "/settings/dictionary/type/index";
    }
    @RequestMapping("/toValueIndex.do")
    public String toValueIndex(){
        return "/settings/dictionary/value/index";
    }

}
