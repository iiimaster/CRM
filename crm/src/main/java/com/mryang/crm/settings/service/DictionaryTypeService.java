package com.mryang.crm.settings.service;

import com.mryang.crm.exception.AjaxRequestException;
import com.mryang.crm.exception.TraditionRequestException;
import com.mryang.crm.settings.pojo.DictionaryType;
import com.mryang.crm.settings.pojo.DictionaryValue;

import java.util.List;
import java.util.Map;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName DictionaryTypeServiceImpl
 * @Description TODO 数据字典-模块
 * @createTime 2021年09月29日 15:29:00
 */
public interface DictionaryTypeService {

    // 查询所有字典类型
    List<DictionaryType> findAll();

    // 添加字典类型
    int saveType(String code, String name, String describe) throws AjaxRequestException;

    // 数据校验
    DictionaryType checkType(String code) throws AjaxRequestException;

    DictionaryType findByCode(String code) throws TraditionRequestException;

    void updateType(String code, String name, String describe) throws TraditionRequestException;

    void deleteType(String[] ids) throws AjaxRequestException;

    Map<String, List<DictionaryValue>> findDicValueList();
}
