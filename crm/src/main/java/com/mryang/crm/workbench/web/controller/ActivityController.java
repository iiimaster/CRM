package com.mryang.crm.workbench.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mryang.crm.exception.AjaxRequestException;
import com.mryang.crm.settings.pojo.User;
import com.mryang.crm.settings.service.UserService;
import com.mryang.crm.utils.HandleFlag;
import com.mryang.crm.workbench.pojo.Activity;
import com.mryang.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private ActivityService activityService;


    /**
     * 跳转到市场活动页面，展示所有市场活动数据
     * @return
     */
    @RequestMapping("/toindex.do")
    public String toindex(Model model) throws AjaxRequestException {

        // 用户信息查询
        List<User> users = userService.queryAllUser();

        // 市场活动类表数据查询-未分页
        List<Activity> activities = activityService.getActivityList();
        if (activities == null){
            throw new AjaxRequestException("数据列表加载失败");
        }
        model.addAttribute("activities",activities);
        // 将用户数据放入作用域
        model.addAttribute("users",users);

        return "/workbench/activity/index";
    }


    /**
     * 市场活动数据列表查询-分页查询
     *
     * @param model
     * @return
     */
    @RequestMapping("/getActivitisByPageHelper.do")
    public String getActivityList(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "8") int pageSize,
                                  Model model) throws AjaxRequestException {

        // 用户信息查询
        List<User> users = userService.queryAllUser();


        // 等同于 limit a,b  使用拦截器实现的
        PageHelper.startPage(page, pageSize);

        // 查询数据
        List<Activity> activities = activityService.getActivityList();

        if (activities == null){
            throw new AjaxRequestException("市场活动数据列表查询失败");
        }

        PageInfo pageInfo = new PageInfo<Activity>(activities);

        // 总记录数
        Long count = pageInfo.getTotal();

        // 总页数
        int pages = pageInfo.getPages();

        // 获取要 加载页 的 第一条数据索引(数据库数据索引从0开始)
        int pageNum = (page - 1) * pageSize;


        // 将数据放入作用域
        // 用户列表数据
        model.addAttribute("users", users);
        // 总记录数
        model.addAttribute("count", count);
        // 当前页码
        model.addAttribute("pageNow", page);
        // 市场活动列表数据
        model.addAttribute("activities", pageInfo.getList());
        // 总页数
        model.addAttribute("pages", pages);
        // 每页显示条数
        model.addAttribute("pageSize", pageSize);

        // 转发到列表页
        return "/workbench/activity/index";
    }


    /**
     * 市场活动细节页面显示-跳转
     * @return
     */
    @RequestMapping("/toDetail.do")
    public String toDetail(){
        return "/workbench/activity/detail";
    }



}
