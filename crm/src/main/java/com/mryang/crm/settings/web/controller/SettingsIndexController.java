package com.mryang.crm.settings.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName SettingsIndexController.java
 * @Description TODO 跳转系统设置首页面
 * @createTime 2021年09月29日 14:20:00
 */
@Controller
@RequestMapping("/settings")
public class SettingsIndexController {
    /**
     * 跳转到 系统设置 页面
     * @return
     */
    @RequestMapping("/toSettings.do")
    public String toSettings(){
        return "/settings/index";
    }
}
