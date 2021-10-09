package com.mryang.crm.workbench.web.controller;

import com.mryang.crm.settings.pojo.User;
import com.mryang.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName ActivityController.java
 * @Description TODO 工作台-市场活动
 * @createTime 2021年10月09日 10:51:00
 */
@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {

    @Autowired
    private UserService userService;


    /**
     * 跳转到市场活动页面
     * @return
     */
    @RequestMapping("/toindex.do")
    public String toindex(Model model){

        // 用户信息查询
        List<User> users = userService.queryAllUser();

        // 将用户数据放入作用域
        model.addAttribute("users",users);

        return "/workbench/activity/index";
    }

    /**
     * 市场活动细节页面显示
     * @return
     */
    @RequestMapping("/toDetail.do")
    public String toDetail(){
        return "/workbench/activity/detail";
    }



}
