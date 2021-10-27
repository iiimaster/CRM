package com.mryang.crm.workbench.service;

import com.mryang.crm.workbench.pojo.Clue;

import java.util.List;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName ClueServiceImpl.java
 * @Description TODO
 * @createTime 2021年10月20日 21:02:00
 */
public interface ClueService {

    Clue queryClueById(String id);

    // 分页查询
    List<Clue> queryClueListByPage(Integer pageNo, Integer pageSize);

    int clueListCount();

    // 条件检索
    List<Clue> queryClueByRetrieve(String fullname, String company, String phone, String source, String createBy, String myphone, String state);
}
