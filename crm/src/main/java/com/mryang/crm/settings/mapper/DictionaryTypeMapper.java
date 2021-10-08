package com.mryang.crm.settings.mapper;

import com.mryang.crm.settings.pojo.DictionaryType;
import com.mryang.crm.settings.pojo.DictionaryValue;
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

    /**
     * 插入数据
     * @param code
     * @param name
     * @param describe
     * @return
     */
    int saveType(@Param("code") String code,
                 @Param("name") String name,
                 @Param("describe") String describe);

    /**
     * 根据主键查询数据
     * @param code
     * @return
     */
    DictionaryType queryType(String code);

    /**
     * 修改数据
     * @param editId
     * @param code
     * @param name
     * @param describe
     * @return
     */
    int updateTypeByEditId(
            @Param("editId") String editId,
            @Param("code") String code,
            @Param("name") String name,
            @Param("describe") String describe);

    int updateType(@Param("code") String code, @Param("name") String name, @Param("describe") String describe);

    int deleteType(String id);

}
