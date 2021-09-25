package com.mryang.crm.settings.web.controller;

import com.mryang.crm.exception.LoginException;
import com.mryang.crm.settings.pojo.User;
import com.mryang.crm.settings.service.UserService;
import com.mryang.crm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName UserController.java
 * @Description TODO 系统设置模块--用户模块
 * @createTime 2021年09月24日 11:50:00
 */
@Controller
@RequestMapping("/settings/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录功能
     *
     * @param loginAct
     * @param loginPwd
     * @return json串
     */
    @RequestMapping("/login.do")
    @ResponseBody
    public Map<String, Object> login(String loginAct, String loginPwd, HttpServletRequest request) throws LoginException {

        // 获取ip地址
        String ip = request.getRemoteAddr();
        System.out.println("当前的ip ::>> "+ip);

        // 由于数据库中的密码都是经过MD5的加密算法加密过的，
        // 所以我们在登录的时候需要对密码进行加密操作
        String md5Pwd = MD5Util.getMD5(loginPwd);

        // 根据用户名和密码查询用户信息，是否能够登录
        User loginUser = userService.login(loginAct, md5Pwd, ip);

        Map<String, Object> resultMap = new HashMap<>();

        if (loginUser == null) {
            // 查询失败，用户名或密码错误
            resultMap.put("success", false);
            resultMap.put("msg", "用户名或密码错误");
            return resultMap;
        }

        resultMap.put("success", true);
        resultMap.put("msg", "登录成功");

        // 将用户存入到session 中，后续进行权限控制
        request.getSession().setAttribute("user", loginUser);

        return resultMap;

    }



}
