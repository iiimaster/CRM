package com.mryang.crm.settings.service.impl;

import com.mryang.crm.settings.mapper.UserMapper;
import com.mryang.crm.settings.pojo.User;
import com.mryang.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public User login(String loginAct, String loginPwd) {

        User user = userMapper.queryLoginUser(loginAct, loginPwd);

        System.out.println("user ::>> "+ user);
        if (user == null){
            return null;
        }

        return user;
    }
}
