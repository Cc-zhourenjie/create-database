package com.cc.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 表字段对象
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldInfoVO implements Serializable {

    /**
     * 字段名
     */
    @JsonProperty("field_name")
    private String fieldName;

    /**
     * 字段类型
     */
    @JsonProperty("field_type")
    private String fieldType;

    /**
     * 字段类型
     */
    @JsonProperty("field_length")
    private String fieldLength;

    /**
     * 字段注释
     */
    @JsonProperty("remark")
    private String remark;

    /**
     * 是否可以为空
     */
    @JsonProperty("is_null")
    private Integer isNull;


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(String fieldLength) {
        this.fieldLength = fieldLength;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsNull() {
        return isNull;
    }

    public void setIsNull(Integer isNull) {
        this.isNull = isNull;
    }
}
