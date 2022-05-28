package com.cc.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 数据库信息对象
 *
 * @author zrj
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableInfoVO implements Serializable {

    /**
     * 表名
     */
    @JsonProperty("table_name")
    private String tableName;

    /**
     * 表注释
     */
    @JsonProperty("table_comment")
    private String tableComment;

    /**
     * 主键字段
     */
    @JsonProperty("field_name")
    private String fieldName;

    /**
     * 主键字段中文名
     */
    @JsonProperty("field_name_en")
    private String fieldNameEn;

    /**
     * 主键字段类型
     */
    @JsonProperty("field_type")
    private String fieldType;

    /**
     * 主键字段长度
     */
    @JsonProperty("field_length")
    private Integer fieldLength;

    /**
     * 字段小数位数
     */
    @JsonProperty("field_decimal")
    private Integer fieldDecimal;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldNameEn() {
        return fieldNameEn;
    }

    public void setFieldNameEn(String fieldNameEn) {
        this.fieldNameEn = fieldNameEn;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Integer getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(Integer fieldLength) {
        this.fieldLength = fieldLength;
    }


    public Integer getFieldDecimal() {
        return fieldDecimal;
    }

    public void setFieldDecimal(Integer fieldDecimal) {
        this.fieldDecimal = fieldDecimal;
    }
}
