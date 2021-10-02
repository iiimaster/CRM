package com.mryang.crm.settings.pojo;

import java.io.Serializable;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName DictionaryType.java
 * @Description TODO 数据字典-类型-实体类
 * @createTime 2021年09月29日 15:35:00
 */
public class DictionaryType implements Serializable {

    private String code;
    private String name;
    private String description;

    @Override
    public String toString() {
        return "DictionaryType{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
