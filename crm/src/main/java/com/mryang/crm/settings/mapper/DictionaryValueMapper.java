package com.mryang.crm.settings.mapper;

import com.mryang.crm.settings.pojo.DictionaryValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName DictionaryValueMapper.java
 * @Description TODO
 * @createTime 2021年10月08日 15:57:00
 */
public interface DictionaryValueMapper {
    List<DictionaryValue> findAllValues();

    int saveValue(@Param("uuid") String uuid,
                  @Param("typeCode") String typeCode,
                  @Param("value") String value,
                  @Param("text") String text,
                  @Param("orderNo") String orderNo);

    DictionaryValue findValueById(String id);

    int updateValue(DictionaryValue dictionaryValue);

    int deleteValueById(String id);

    int deleteValueByTypeCode(String id);
}
