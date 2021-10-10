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


    List<Activity> getActivityList();

}
