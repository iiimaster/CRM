package com.mryang.crm.workbench.service.impl;

import com.mryang.crm.workbench.mapper.ClueMapper;
import com.mryang.crm.workbench.pojo.Clue;
import com.mryang.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName ClueServiceImpl.java
 * @Description TODO
 * @createTime 2021年10月20日 21:02:00
 */
@Service
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueMapper clueMapper;


    @Override
    public Clue queryClueById(String id) {
        return clueMapper.queryClueById(id);
    }

    @Override
    public List<Clue> queryClueListByPage(Integer pageNo, Integer pageSize) {
        return clueMapper.queryClueListByPage(pageNo,pageSize);
    }

    @Override
    public int clueListCount() {
        return clueMapper.clueListCount();
    }

    @Override
    public List<Clue> queryClueByRetrieve(String fullname, String company, String phone, String source, String createBy, String myphone, String state) {
        return clueMapper.queryClueByRetrieve(fullname,company,phone,source,createBy,myphone,state);
    }
}
