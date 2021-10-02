package com.mryang.crm.settings.mapper;

import com.mryang.crm.settings.pojo.DictionaryType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName DictionaryTypeMapper.java
 * @Description TODO
 * @createTime 2021年09月29日 15:40:00
 */
public interface DictionaryTypeMapper {

    /**
     * 查询-数据字典-类型-所有数据
     * @return
     */
    List<DictionaryType> findAll();

    int saveType(@Param("code") String code,
                 @Param("name") String name,
                 @Param("describe") String describe);
}
