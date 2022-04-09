package com.cc.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 数据库信息对象
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableInfoVO implements Serializable {

    /**
     * 表名
     */
    @JsonProperty("table_name")
    private String tableName;

    /**
     * 注释
     */
    @JsonProperty("remark")
    private String remark;

    /**
     * 主键字段
     */
    @JsonProperty("primary_field_name")
    private String primaryFieldName;

    /**
     * 主键字段类型
     */
    @JsonProperty("primary_field_type")
    private String primaryFieldType;

    /**
     * 主键字段长度
     */
    @JsonProperty("primary_field_length")
    private Integer primaryFieldLength;

    /**
     * 字段集合
     */
    @JsonProperty("fields")
    private List<FieldInfoVO> fieldInfoVOS;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPrimaryFieldName() {
        return primaryFieldName;
    }

    public void setPrimaryFieldName(String primaryFieldName) {
        this.primaryFieldName = primaryFieldName;
    }

    public String getPrimaryFieldType() {
        return primaryFieldType;
    }

    public void setPrimaryFieldType(String primaryFieldType) {
        this.primaryFieldType = primaryFieldType;
    }

    public Integer getPrimaryFieldLength() {
        return primaryFieldLength;
    }

    public void setPrimaryFieldLength(Integer primaryFieldLength) {
        this.primaryFieldLength = primaryFieldLength;
    }

    public List<FieldInfoVO> getFieldInfoVOS() {
        return fieldInfoVOS;
    }

    public void setFieldInfoVOS(List<FieldInfoVO> fieldInfoVOS) {
        this.fieldInfoVOS = fieldInfoVOS;
    }
}
