package com.cc;

import com.cc.utils.CcSettingConfig;
import com.cc.utils.DBUtil;
import org.junit.jupiter.api.Test;

public class TestController {


    @Test
    public void testA() {
        CcSettingConfig settingConfig = CcSettingConfig.buildXML("core", "test");
        String createTableSql = settingConfig.getText("create_table");
        createTableSql = createTableSql.replace("#{table_name}", "test")
                .replace("#{field_name}", "id")
                .replace("#{field_type}", "varchar(50)")
                .replace("#{remark}", "测试数据表");
        DBUtil.changeDate(createTableSql);
        System.out.println();
    }


}
