package com.cc.service.impl;

import com.cc.mybaits.utils.CcMapperUtils;
import com.cc.pojo.vo.TableInfoVO;
import com.cc.service.TableInfoService;
import com.cc.utils.CcSql;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 操作物理表服务层
 *
 * @author cc
 */
@Service
public class TableInfoServiceImpl implements TableInfoService {

    /**
     * 日志服务
     */
    private static final Logger log = LoggerFactory.getLogger(TableInfoServiceImpl.class);

    @Autowired
    private SqlSession sqlSession;

    /**
     * 创建表，基础表结构
     *
     * @return
     */
    public Boolean createTable(TableInfoVO tableInfoVO) {
        try {
            //获取sql
            String createTableSql = CcSql.getSqlText("core", "table_sql", "create_table");
            CcMapperUtils ccMapperUtils = new CcMapperUtils();
            //构架sql条件
            Map<String, Object> condition = new HashMap<>();
            condition.put("table_name", tableInfoVO.getTableName());
            condition.put("table_comment", tableInfoVO.getTableComment());
            condition.put("field_name", tableInfoVO.getFieldName());
            condition.put("field_type", tableInfoVO.getFieldType() + "(" + tableInfoVO.getFieldLength() + ")");
            condition.put("field_comment", tableInfoVO.getFieldNameEn());
            condition.put("table_name", tableInfoVO.getTableName());
            boolean insert = ccMapperUtils.insert(createTableSql, condition);
            return true;
        } catch (Exception e) {
            log.error("新增基础表结构异常", e);
        }
        return false;
    }


}
