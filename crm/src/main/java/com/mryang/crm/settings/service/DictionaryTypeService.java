package com.mryang.crm.settings.service;

import com.mryang.crm.settings.pojo.DictionaryType;

import java.util.List;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName DictionaryTypeServiceImpl
 * @Description TODO 数据字典-模块
 * @createTime 2021年09月29日 15:29:00
 */
public interface DictionaryTypeService {

    List<DictionaryType> findAll();

    int saveType(String code, String name, String describe);
}
