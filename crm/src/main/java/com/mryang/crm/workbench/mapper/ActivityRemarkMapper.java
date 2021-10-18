package com.mryang.crm.workbench.mapper;

import com.mryang.crm.workbench.pojo.ActivityRemark;

import java.util.List;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName ActivityRemarkMapper.java
 * @Description TODO
 * @createTime 2021年10月18日 17:39:00
 */
public interface ActivityRemarkMapper {

    // 根据 市场活动id 查询 相关备注信息
    List<ActivityRemark> queryActivityRemarkListByAid(String activityId);

}
