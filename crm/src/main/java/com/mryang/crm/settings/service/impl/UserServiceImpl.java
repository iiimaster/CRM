package com.mryang.crm.settings.service.impl;

import com.mryang.crm.exception.LoginException;
import com.mryang.crm.settings.mapper.UserMapper;
import com.mryang.crm.settings.pojo.User;
import com.mryang.crm.settings.service.UserService;
import com.mryang.crm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName UserServiceImpl.java
 * @Description TODO
 * @createTime 2021年09月24日 12:32:00
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {

        // 查询 是否有 对应用户信息
        User user = userMapper.queryLoginUser(loginAct, loginPwd);

//        System.out.println("用户信息user ::>> " + user);

        if (user == null) {// 登录用户不存在
            throw new LoginException("用户名或密码错误");
        }

        // 校验
        // 校验锁定状态
        String lockState = user.getLockState();
        if (lockState != null) {
            if ("0".equals(lockState)) {
                throw new LoginException("帐号被锁定");
            }
        }

        // 校验过期时间
        String expireTime = user.getExpireTime();
//        System.out.println("过期时间 ::>> "+expireTime);
        // 获取现在时间
        String nowTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
//        System.out.println("现在时间 ::>> "+nowTime);

        System.out.println(expireTime.compareTo(nowTime));
        if (expireTime != null) {
            if (expireTime.compareTo(nowTime) < 0) {// 时间比较 nowTime > editTime
                throw new LoginException("帐号已过期");
            }
        }

        // ip地址校验
        // 当前浏览器访问的ip地址必须以127.0.0.1开头的
        // 如果使用的是localhost，则ip地址获取会出问题
        String allowIps = user.getAllowIps();
        if (allowIps != null) {
            if (!allowIps.contains(ip)) {// 当前访问地址
                throw new LoginException("ip地址不允许访问");
            }
        }

        return user;
    }

    @Override
    public User queryUser(String loginAct, String md5OldPwd) {
        User user = userMapper.queryLoginUser(loginAct, md5OldPwd);
        if (user == null){
            return null;
        }
        return user;
    }

    @Override
    public int updateUserPwd(String loginId, String md5NewPwd) {

        int i = userMapper.updateUserPwd(loginId, md5NewPwd);

        return i;
    }

    @Override
    public List<User> queryAllUser() {
        return userMapper.queryAllUser();
    }

    @Override
    public User queryUserById(String id) {
        return userMapper.queryUserById(id);
    }


    public static void main(String[] args) {
        String time = "2020/4/5 14:23:34";
        String nowtime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());

//        System.out.println("nowtime  大于 time (nowtime.compareTo(time))  ::>>> " + nowtime.compareTo(time)); // 1
//        System.out.println("time  小于 nowtime (time.compareTo(nowtime))  ::>>> " + time.compareTo(nowtime)); // -1

//        System.out.println(zs);

//        String md5 = MD5Util.getMD5("1");
//        System.out.println(md5);

    }
}
