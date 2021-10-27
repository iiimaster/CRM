package com.mryang.crm.settings.service.impl;

import com.mryang.crm.exception.TraditionRequestException;
import com.mryang.crm.settings.mapper.DictionaryValueMapper;
import com.mryang.crm.settings.pojo.DictionaryValue;
import com.mryang.crm.settings.service.DictionaryValueService;
import com.mryang.crm.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName DictionaryValueServiceImpl.java
 * @Description TODO
 * @createTime 2021年10月08日 15:55:00
 */
@Service
public class DictionaryValueServiceImpl implements DictionaryValueService {

    @Autowired
    private DictionaryValueMapper dictionaryValueMapper;


    @Override
    public List<DictionaryValue> findAllValues() {
        return dictionaryValueMapper.findAllValues();
    }

    @Override
    public int saveValue(String typeCode, String value, String text, String orderNo) {

        // 设置字典值的编号（使用uuid自动生成32位编码，与数据库吻合）
        String uuid = UUIDUtil.getUUID();

        return dictionaryValueMapper.saveValue(uuid ,typeCode, value, text, orderNo);
    }

    @Override
    public DictionaryValue findValueById(String id) {
        return dictionaryValueMapper.findValueById(id);
    }

    @Override
    public int updateValue(DictionaryValue dictionaryValue) {
        return dictionaryValueMapper.updateValue(dictionaryValue);
    }

    @Override
    public void deleteValueById(String ids) throws TraditionRequestException {

        // 将数据ids分割成单个id
        String[] delIds = ids.split("-");


        for (String id : delIds) {
            // 删除操作
            int i = dictionaryValueMapper.deleteValueById(id);

            if (i<=0){
                throw new TraditionRequestException("数据删除失败");
            }
        }



    }

    @Override
    public List<DictionaryValue> getClueState() {
        return dictionaryValueMapper.getClueState();
    }

    @Override
    public List<DictionaryValue> getClueSource() {
        return dictionaryValueMapper.getClueSource();
    }
}
