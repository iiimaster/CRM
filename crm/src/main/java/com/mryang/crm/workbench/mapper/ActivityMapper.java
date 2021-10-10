package com.mryang.crm.workbench.mapper;

import com.mryang.crm.workbench.pojo.Activity;

import java.util.List;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName ActivityMapper.java
 * @Description TODO
 * @createTime 2021年10月10日 12:36:00
 */
public interface ActivityMapper {

    /**
     * 查询所有市场活动数据
     * @return
     */
    List<Activity> getActivityList();

}
