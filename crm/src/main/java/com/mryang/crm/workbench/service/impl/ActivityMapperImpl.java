package com.mryang.crm.workbench.service.impl;

import com.mryang.crm.workbench.mapper.ActivityMapper;
import com.mryang.crm.workbench.pojo.Activity;
import com.mryang.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName ActivityMapperImpl.java
 * @Description TODO
 * @createTime 2021年10月10日 12:35:00
 */
@Service
public class ActivityMapperImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;


    @Override
    public List<Activity> getActivityList() {
        return activityMapper.getActivityList();
    }

    @Override
    public int saveActivity(String id, String owner, String name, String startDate, String endDate, String cost, String description, String createTime, String createBy) {
        return activityMapper.saveActivity(id,owner,name,startDate,endDate,cost,description,createTime,createBy);
    }

    @Override
    public Activity queryActivityById(String id) {
        return activityMapper.queryActivityById(id);
    }

    @Override
    public int updateActivity(String id,String owner, String name, String startDate, String endDate, String cost, String description, String editBy, String editTime) {
        return activityMapper.updateActivity(id,owner,name,startDate,endDate,cost,description,editBy,editTime);
    }
}
