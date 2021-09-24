package com.mryang.crm.settings.mapper;

import com.mryang.crm.settings.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName UserMapper.java
 * @Description TODO
 * @createTime 2021年09月24日 12:59:00
 */

public interface UserMapper {

    User queryLoginUser(@Param("loginAct") String loginAct,
                        @Param("loginPwd") String loginPwd);

}
