package com.mryang.crm.settings.pojo;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName DictionaryValue.java
 * @Description TODO
 * @createTime 2021年10月05日 18:36:00
 */
public class DictionaryValue {

    private String id;
    private String value;
    private String text;
    private String orderNo;
    private String typeCode;

    @Override
    public String toString() {
        return "DictionaryValue{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                ", text='" + text + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", typeCode='" + typeCode + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
