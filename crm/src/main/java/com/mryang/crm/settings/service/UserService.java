package com.mryang.crm.settings.service;

import com.mryang.crm.exception.LoginException;
import com.mryang.crm.settings.pojo.User;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName UserService.java
 * @Description TODO 系统设置模块--用户模块控制层
 * @createTime 2021年09月24日 12:28:00
 */
public interface UserService {

    // 用户登录
    User login(String loginAct, String loginPwd, String ip) throws LoginException;

}
