package com.mryang.crm.workbench.mapper;

import com.mryang.crm.workbench.pojo.Activity;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 添加市场活动数据
     * @param id 市场活动编号
     * @param owner 操作者编号
     * @param name 市场活动名称
     * @param startData 开始时间
     * @param endData 结束时间
     * @param cost 成本
     * @param description 描述
     * @param createTime 创建时间
     * @param createBy 创建人
     * @return
     */
    int saveActivity(@Param("id") String id,
                     @Param("owner") String owner,
                     @Param("name") String name,
                     @Param("startDate") String startDate,
                     @Param("endDate") String endDate,
                     @Param("cost") String cost,
                     @Param("description") String description,
                     @Param("createTime") String createTime,
                     @Param("createBy") String createBy);

    /**
     * 根据id查询市场活动信息
     * @param id
     * @return
     */
    Activity queryActivityById(String id);

    int updateActivity(@Param("id")String id,
                       @Param("owner")String owner,
                       @Param("name")String name,
                       @Param("startDate")String startDate,
                       @Param("endDate")String endDate,
                       @Param("cost")String cost,
                       @Param("description")String description,
                       @Param("editBy")String editBy,
                       @Param("editTime")String editTime);

    int deleteByIds(String id);
}
