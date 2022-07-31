package com.cc.service.impl;

import com.cc.mybaits.utils.CcMapperUtils;
import com.cc.pojo.vo.FieldInfoVO;
import com.cc.service.FieldInfoService;
import com.cc.utils.CcSql;
import com.cc.utils.CcString;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 操作物理表字段服务层
 *
 * @author cc
 */
@Service
public class FieldInfoServiceImpl implements FieldInfoService {

    /**
     * 日志服务
     */
    private static final Logger log = LoggerFactory.getLogger(FieldInfoServiceImpl.class);

    @Autowired
    private SqlSession sqlSession;


    /**
     * 创建物理表字段数据
     *
     * @param fieldInfoVO
     * @return
     */
    @Override
    public Boolean createField(FieldInfoVO fieldInfoVO) {
        try {
            //获取sql
            String createTableSql = CcSql.getSqlText("core", "field_sql", "create_field");
            CcMapperUtils ccMapperUtils = new CcMapperUtils();
            //判断字段类型和长度
            if (CcString.equal("varchar", fieldInfoVO.getFieldType())) {
                //如果varchar类型没有长度，默认50
                if (CcString.isEmpty(fieldInfoVO.getFieldLength())) {
                    fieldInfoVO.setFieldLength("50");
                }
                fieldInfoVO.setFieldType(fieldInfoVO.getFieldType() + "(" + fieldInfoVO.getFieldLength() + ")");
            }
            //小数精度
            else if (CcString.equal("decimal", fieldInfoVO.getFieldType())) {
                //如果decimal类型没有长度，默认50
                if (CcString.isEmpty(fieldInfoVO.getFieldLength())) {
                    fieldInfoVO.setFieldLength("50");
                }
                //判断精度
                if (fieldInfoVO.getFieldPrecision() == null) {
                    fieldInfoVO.setFieldPrecision(0);
                }
                fieldInfoVO.setFieldLength(fieldInfoVO.getFieldLength() + "," + fieldInfoVO.getFieldPrecision());
                fieldInfoVO.setFieldType(fieldInfoVO.getFieldType() + "(" + fieldInfoVO.getFieldLength() + ")");
            }
            //整数
            if (CcString.equal("int", fieldInfoVO.getFieldType())) {
                if (CcString.isEmpty(fieldInfoVO.getFieldLength()) || CcString.equal("0", fieldInfoVO.getFieldLength())) {
                    fieldInfoVO.setFieldLength("32");
                }
                fieldInfoVO.setFieldType(fieldInfoVO.getFieldType() + "(" + fieldInfoVO.getFieldLength() + ")");
            }
            //构架sql条件
            Map<String, Object> condition = new HashMap<>();
            condition.put("table_name", fieldInfoVO.getTableName());
            condition.put("field_name", fieldInfoVO.getFieldName());
            condition.put("field_type", fieldInfoVO.getFieldType());
            condition.put("is_null", fieldInfoVO.getIsNull());
            condition.put("remark", fieldInfoVO.getRemark());
            return ccMapperUtils.insert(createTableSql, condition);
        } catch (Exception e) {
            log.error("新增表字段异常", e);
        }
        return false;
    }
}
