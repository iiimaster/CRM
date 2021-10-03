package com.mryang.crm.settings.service.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.mryang.crm.exception.AjaxRequestException;
import com.mryang.crm.settings.mapper.DictionaryTypeMapper;
import com.mryang.crm.settings.pojo.DictionaryType;
import com.mryang.crm.settings.service.DictionaryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName DictionaryTypeServiceImpl.java
 * @Description TODO
 * @createTime 2021年09月29日 15:33:00
 */
@Service
public class DictionaryTypeServiceImpl implements DictionaryTypeService {

    @Autowired
    private DictionaryTypeMapper dictionaryTypeMapper;

    @Override
    public List<DictionaryType> findAll() {

        return dictionaryTypeMapper.findAll();
    }

    @Override
    public int saveType(String code, String name, String describe) throws AjaxRequestException {

            // 添加数据
            int i = dictionaryTypeMapper.saveType(code, name, describe);
            //        System.out.println("i :: >> "+i);

            if (i<=0){
                throw new AjaxRequestException("字典类型保存失败！！！");
            }

            return i;
        }

    @Override
    public void checkType(String code) throws AjaxRequestException {

        if (code == null || code == "") {
            throw new AjaxRequestException("字典类型编码必须填写");
        }

        // 根据字典编码查询字典类型
        DictionaryType checkCode = dictionaryTypeMapper.queryType(code);
        System.out.println("checkCode :::>> "+checkCode);

        if (checkCode != null) {
            throw new AjaxRequestException("字典类型已经存在，不能重复添加！");
        }

    }


}
