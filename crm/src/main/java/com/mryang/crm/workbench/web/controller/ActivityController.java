package com.mryang.crm.workbench.web.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mryang.crm.exception.AjaxRequestException;
import com.mryang.crm.settings.pojo.User;
import com.mryang.crm.settings.service.UserService;
import com.mryang.crm.utils.DateTimeUtil;
import com.mryang.crm.utils.HandleFlag;
import com.mryang.crm.utils.UUIDUtil;
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

import java.util.Date;
import java.util.HashMap;
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
     *
     * @return
     */
    @RequestMapping("/toindex.do")
    public String toindex(Model model) throws AjaxRequestException {

        // 用户信息查询
        List<User> users = userService.queryAllUser();

        // 市场活动类表数据查询-未分页
        List<Activity> activities = activityService.getActivityList();
        if (activities == null) {
            throw new AjaxRequestException("数据列表加载失败");
        }
        model.addAttribute("activities", activities);
        // 将用户数据放入作用域
        model.addAttribute("users", users);

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


        // 等同于 limit a,b  使用拦截器实现的
        PageHelper.startPage(page, pageSize);

        // 查询数据
        List<Activity> activities = activityService.getActivityList();

        if (activities == null && activities.size() <= 0) {
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
     * 跳转到添加数据界面
     * @return
     * @throws AjaxRequestException
     */
    @RequestMapping("/toSaveActivity.do")
    @ResponseBody
    public Map<String, Object> toSaveActivity() throws AjaxRequestException {
        // 用户信息查询
        List<User> users = userService.queryAllUser();

        if (users == null && users.size() <= 0) {
            throw new AjaxRequestException("用户信息查询失败");
        }

        // 返回响应信息
        return HandleFlag.successObj("data", users);
    }

    /**
     * 添加数据操作
     * @param owner
     * @param name
     * @param startDate
     * @param endDate
     * @param cost
     * @param description
     * @return
     * @throws AjaxRequestException
     */
    @RequestMapping("/saveActivity.do")
    @ResponseBody
    public Map<String, Object> saveActivity(String owner,
                                            String name,
                                            String startDate,
                                            String endDate,
                                            String cost,
                                            String description) throws AjaxRequestException {

        // 获取创建者姓名
        User user = userService.queryUserById(owner);
        /*System.out.println("user:::>>>"+user);
        System.out.println("owner:::>>>"+owner);
        System.out.println("name:::>>>"+name);
        System.out.println("startDate:::>>>"+startDate);
        System.out.println("endDate:::>>>"+endDate);
        System.out.println("cost:::>>>"+cost);
        System.out.println("description:::>>>"+description);*/

        if (user == null){ // 用户信息不存在
            throw new AjaxRequestException("创建者用户信息错误");
        }

        // 获取当前时间为创建时间
        String createTime = DateTimeUtil.getSysTime();

        // 开始时间不能比结束时间晚
        if (startDate.compareTo(endDate) > 0){ // 开始时间 > 结束时间
            throw new AjaxRequestException("开始时间不能晚于结束时间");
        }

        // 获取一个随机uuId
        String activityId = UUIDUtil.getUUID();

        // 添加市场活动数据
        int i = activityService.saveActivity(activityId, owner, name, startDate, endDate, cost, description, createTime, user.getName());

        if (i <= 0){
            throw new AjaxRequestException("市场活动数据添加失败");
        }

        return HandleFlag.successTrue();
    }


    /**
     * 修改页面跳转，数据加载
     * @return
     */
    @RequestMapping("/toUpdate.do")
    @ResponseBody
    public Map<String,Object> toUpdate(String id,Model model) throws AjaxRequestException {

        List<User> users = userService.queryAllUser();

        if(users == null && users.size() <=0){
            throw new AjaxRequestException("无法获取用户信息");
        }

        Activity activity = activityService.queryActivityById(id);

        if (activity == null ){
            throw new AjaxRequestException("要修改的市场活动信息不存在");
        }

        HashMap<String, Object> resultMap = new HashMap<>();

        resultMap.put("success",true);
        resultMap.put("users",users);
        resultMap.put("activity",activity);

        return resultMap;
    }


    /**
     * 修改市场活动
     * @param editBy
     * @param id
     * @param owner
     * @param name
     * @param startDate
     * @param endDate
     * @param cost
     * @param description
     * @return
     * @throws AjaxRequestException
     */
    @RequestMapping("/updateActivity.do")
    @ResponseBody
    public Map<String,Object> updateActivity(String editBy,
                                             String id,
                                             String owner,
                                             String name,
                                             String startDate,
                                             String endDate,
                                             String cost,
                                             String description) throws AjaxRequestException {

        // 获取当前时间，作为修改时间
        String editTime = DateTimeUtil.getSysTime();
        // 获取操作者名称
        User user = userService.queryUserById(editBy);
        if (user == null ){
            throw new AjaxRequestException("修改操作失败！-操作者信息有误");
        }
        editBy = user.getName();

        // 修改操作
        int i = activityService.updateActivity(id, owner, name, startDate, endDate, cost, description, editBy, editTime);

        if (i<=0){
            throw new AjaxRequestException("修改数据失败！");
        }

        return HandleFlag.successTrue();
    }


    /**
     * 根据id删除市场活动数据
     * @param ids
     * @return
     * @throws AjaxRequestException
     */
    @RequestMapping("/deleteActivityByIds.do")
    @ResponseBody
    public Map<String ,Object> deleteActivityByIds(String ids) throws AjaxRequestException {

        // 删除数据
        activityService.deleteActivityByIds(ids);

//        return HandleFlag.error("msg","error");
        return HandleFlag.successTrue();
    }



    /**
     * 市场活动细节页面显示-跳转
     *
     * @return
     */
    @RequestMapping("/toDetail.do")
    public String toDetail(String id,Model model) {

        // 根据id查询市场活动详细信息
        Activity activity = activityService.queryActivityById(id);


        // 放入作用域
        model.addAttribute("activity",activity);

        return "/workbench/activity/detail";
    }


    

    public static void main(String[] args) {
        String sysTime = DateTimeUtil.getSysTime();
        System.out.println(sysTime);

        String sysTimeForUpload = DateTimeUtil.getSysTimeForUpload();
        System.out.println(sysTimeForUpload);




        String date = "2021-6-6 12:23:34";

//
//        String sysTimeForUpload1 = DateTimeUtil.getSysTimeForUpload(date);
//        System.out.println(sysTimeForUpload1);


    }


}
