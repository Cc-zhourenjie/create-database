package com.cc.service.impl;

import com.cc.pojo.vo.TableInfoVO;
import com.cc.service.TableInfoService;
import com.cc.utils.CcSettingConfig;
import com.cc.utils.CcString;
import com.cc.utils.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 数据表服务层
 */
@Service
public class TableInfoServiceImpl implements TableInfoService {


    private static final Logger log = LoggerFactory.getLogger(TableInfoServiceImpl.class);

    /**
     * 创建表，基础表结构
     *
     * @return
     */
    public Boolean createTable(TableInfoVO tableInfoVO) {
        try {
            String tableName = tableInfoVO.getTableName();
            String remark = " ";
            if (CcString.isNotEmpty(tableInfoVO.getRemark())) {
                remark = tableInfoVO.getRemark();
            }
            String fieldName = tableInfoVO.getPrimaryFieldName();
            String fieldType = tableInfoVO.getPrimaryFieldType();
            if (CcString.equal("varchar", tableInfoVO.getPrimaryFieldType()) || CcString.equal("decimal", tableInfoVO.getPrimaryFieldType())) {
                fieldType += "(" + tableInfoVO.getPrimaryFieldLength() + ")";
            }
            CcSettingConfig settingConfig = CcSettingConfig.buildXML("core", "test");
            String createTableSql = settingConfig.getText("create_table");
            createTableSql = createTableSql.replace("#{table_name}", tableName)
                    .replace("#{field_name}", fieldName)
                    .replace("#{field_type}", fieldType)
                    .replace("#{remark}", remark);
            DBUtil.changeDate(createTableSql);
            return true;
        } catch (Exception e) {
            log.error("新增基础表结构异常", e);
        }
        return false;
    }


}
