package com.mryang.crm.workbench.mapper;

import com.mryang.crm.workbench.pojo.ActivityRemark;
import org.apache.ibatis.annotations.Param;

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

    // 添加备注信息
    int saveActivityRemark(@Param("id") String id,
                           @Param("noteContent") String noteContent,
                           @Param("createTime") String createTime,
                           @Param("createBy") String createBy,
                           @Param("editFlag") String editFlag,
                           @Param("activityId") String activityId);

    int deleteRemarkById(String id);

    ActivityRemark queryRemarkById(String id);

    int updateRemark(@Param("id") String id,
                     @Param("noteContent") String noteContent,
                     @Param("editBy") String editBy,
                     @Param("editTime") String editTime,
                     @Param("editFlag") String editFlag);

}
