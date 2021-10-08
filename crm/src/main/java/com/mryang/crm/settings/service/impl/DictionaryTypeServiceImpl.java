package com.mryang.crm.settings.service.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.mryang.crm.exception.AjaxRequestException;
import com.mryang.crm.exception.TraditionRequestException;
import com.mryang.crm.settings.mapper.DictionaryTypeMapper;
import com.mryang.crm.settings.pojo.DictionaryType;
import com.mryang.crm.settings.pojo.DictionaryValue;
import com.mryang.crm.settings.service.DictionaryTypeService;
import com.mryang.crm.utils.UUIDUtil;
import org.apache.poi.ss.formula.functions.T;
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
    public DictionaryType checkType(String code){

        // 根据字典编码查询字典类型
        DictionaryType checkCode = dictionaryTypeMapper.queryType(code);
//        System.out.println("checkCode :::>> "+checkCode);

        return checkCode;

    }

    @Override
    public DictionaryType findByCode(String code) throws TraditionRequestException {

        DictionaryType dictionaryType = dictionaryTypeMapper.queryType(code);
        if (dictionaryType == null){
            throw new TraditionRequestException("要编辑的数据异常");
        }
        return dictionaryType;
    }

    @Override
    public void updateType(String code, String name, String describe) throws TraditionRequestException {

        // 修改原数据的主键，且数据库中没有此数据，则需要通过旧的主键来修改数据
        int i = dictionaryTypeMapper.updateType(code, name, describe);

        if (i<=0){
            throw new TraditionRequestException("数据修改异常");
        }


    }

    @Override
    public void deleteType(String[] ids) throws AjaxRequestException {

        for (String id : ids) {

            int i = dictionaryTypeMapper.deleteType(id);

//            i=0;

            if (i<=0){
                throw new AjaxRequestException("数据删除失败");
            }
        }
    }

    @Override
    public List<DictionaryValue> findAllValues() {
        return dictionaryTypeMapper.findAllValues();
    }

    @Override
    public int saveValue(String typeCode, String value, String text, String orderNo) {

        // 设置字典值的编号（使用uuid自动生成32位编码，与数据库吻合）
        String uuid = UUIDUtil.getUUID();

        return dictionaryTypeMapper.saveValue(uuid ,typeCode, value, text, orderNo);
    }

    @Override
    public DictionaryValue findValueById(String id) {
        return dictionaryTypeMapper.findValueById(id);
    }

    @Override
    public int updateValue(DictionaryValue dictionaryValue) {
        return dictionaryTypeMapper.updateValue(dictionaryValue);
    }


}
