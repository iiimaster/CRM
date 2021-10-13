package com.mryang.crm.workbench.service;

import com.mryang.crm.workbench.pojo.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName ActivityService.java
 * @Description TODO
 * @createTime 2021年10月10日 12:33:00
 */
public interface ActivityService {

    // 查询所有数据
    List<Activity> getActivityList();

    // 添加数据
    int saveActivity(String id, String owner, String name, String startDate, String endDate, String cost, String description, String createTime, String createBy);
}
