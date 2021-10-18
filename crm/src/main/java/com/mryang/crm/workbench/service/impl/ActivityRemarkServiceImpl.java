package com.mryang.crm.workbench.service.impl;

import com.mryang.crm.workbench.mapper.ActivityRemarkMapper;
import com.mryang.crm.workbench.pojo.ActivityRemark;
import com.mryang.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName ActivityRemarkServiceImpl.java
 * @Description TODO
 * @createTime 2021年10月18日 17:39:00
 */
@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

    @Autowired
    private ActivityRemarkMapper activityRemarkMapper;

    @Override
    public List<ActivityRemark> queryActivityRemarkListByAid(String activityId) {

        return activityRemarkMapper.queryActivityRemarkListByAid(activityId);
    }
}
