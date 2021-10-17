package com.mryang.crm.workbench.service;

import com.mryang.crm.exception.AjaxRequestException;
import com.mryang.crm.workbench.pojo.Activity;

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

    // 根据id查询数据
    Activity queryActivityById(String id);

    // 修改数据
    int updateActivity(String id,String owner, String name, String startDate, String endDate, String cost, String description, String editBy, String editTime);

    // 根据id删除数据
    void deleteActivityByIds(String ids) throws AjaxRequestException;

    // 部分导出数据
    List<Activity> getActivityListByIds(String ids) throws AjaxRequestException;

    int saveImportActivity(List<Activity> activityList) throws AjaxRequestException;
}
