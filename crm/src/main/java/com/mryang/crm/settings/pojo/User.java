package com.mryang.crm.settings.pojo;

import java.io.Serializable;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName User.java
 * @Description TODO 用户实体类
 * @createTime 2021年09月24日 12:36:00
 */
public class User implements Serializable {
    // 用户编号
    private String id;

    // 用户显示昵称
    private String name;

    // 用户名
    private String loginAct;

    // 密码
    private String loginPwd;

    // 邮箱
    private String email;

    // 部门编号
    private String deptno;

    // 过期时间
    private String expireTime;

    // 创建人
    private String createBy;

    // 创建时间
    private String createTime;

    // 修改人
    private String editBy;

    // 修改时间
    private String editTime;

    // 允许访问的ip地址
    private String allowIps;

    // 锁定状态
    private String lockState;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", loginAct='" + loginAct + '\'' +
                ", loginPwd='" + loginPwd + '\'' +
                ", email='" + email + '\'' +
                ", deptno='" + deptno + '\'' +
                ", expireTime='" + expireTime + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime='" + createTime + '\'' +
                ", editBy='" + editBy + '\'' +
                ", editTime='" + editTime + '\'' +
                ", allowIps='" + allowIps + '\'' +
                ", lockState='" + lockState + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getLoginAct() {
        return loginAct;
    }

    public void setLoginAct(String loginAct) {
        this.loginAct = loginAct;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAllowIps() {
        return allowIps;
    }

    public void setAllowIps(String allowIps) {
        this.allowIps = allowIps;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    public String getLockState() {
        return lockState;
    }

    public void setLockState(String lockState) {
        this.lockState = lockState;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
