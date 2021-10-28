package com.mryang.crm.settings.service;

import com.mryang.crm.exception.TraditionRequestException;
import com.mryang.crm.settings.pojo.DictionaryValue;

import java.util.List;
import java.util.Map;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName DictionaryValueService.java
 * @Description TODO
 * @createTime 2021年10月08日 15:54:00
 */
public interface DictionaryValueService {

    List<DictionaryValue> findAllValues();

    int saveValue(String typeCode, String dvValue, String dvText, String dvOrderNo);

    DictionaryValue findValueById(String id);

    int updateValue(DictionaryValue dictionaryValue);

    void deleteValueById(String ids) throws TraditionRequestException;

    // 获取线索状态
    List<DictionaryValue> getClueState();

    // 获取线索来源
    List<DictionaryValue> getClueSource();


}
