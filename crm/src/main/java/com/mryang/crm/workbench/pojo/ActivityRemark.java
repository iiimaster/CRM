package com.mryang.crm.workbench.pojo;

import java.io.Serializable;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName ActivityRemark.java
 * @Description TODO 市场活动-备注信息
 * @createTime 2021年10月18日 17:33:00
 */
public class ActivityRemark implements Serializable {

    // 备注信息编号
    private String id;
    // 备注内容
    private String noteContent;
    // 创建时间
    private String createTime;
    // 创建人
    private String createBy;
    // 修改时间
    private String editTime;
    // 修改人
    private String editBy;
    // 修改标志
    private String editFlag;
    // 对应市场活动编号
    private String activityId;

    @Override
    public String toString() {
        return "ActivityRemark{" +
                "id='" + id + '\'' +
                ", noteContent='" + noteContent + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createBy='" + createBy + '\'' +
                ", editTime='" + editTime + '\'' +
                ", editBy='" + editBy + '\'' +
                ", editFlag='" + editFlag + '\'' +
                ", activityId='" + activityId + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }

    public String getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(String editFlag) {
        this.editFlag = editFlag;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
