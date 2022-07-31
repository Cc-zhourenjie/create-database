package com.cc.controller;

import com.cc.mybaits.utils.CcMapperUtils;
import com.cc.mybaits.utils.MapperUtils;
import com.cc.pojo.vo.TableInfoVO;
import com.cc.service.TableInfoService;
import com.cc.utils.ResultData;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 数据表控制器
 *
 * @author zrj
 */
@RequestMapping("/table/info")
@RestController
public class TableInfoController {

    private static final Logger lg = LoggerFactory.getLogger(TableInfoController.class);

    @Autowired
    private TableInfoService tableInfoService;
    //    @Autowired
//    private CcMapperUtils ccMapperUtils;
    @Autowired
    private SqlSession sqlSession;


    @PostMapping("createtable")
    public ResultData createTable(@RequestBody TableInfoVO tableInfoVO) {
        ResultData resultData = null;
        try {
            resultData = ResultData.init();
            Boolean flag = tableInfoService.createTable(tableInfoVO);
            if (!flag) ResultData.failedMsg("创建数据表异常！");
        } catch (Exception e) {
            lg.error("创建物理表异常", e);
        }
        return resultData;
    }

    @GetMapping("seleuserinfo")
    public ResultData seleUserInfo(@RequestParam String id) {
        ResultData resultData = ResultData.init();
        CcMapperUtils ccMapperUtils = new CcMapperUtils(sqlSession);
        MapperUtils mapperUtils = new MapperUtils(sqlSession);
        List<Map> select = ccMapperUtils.select("select * from user where a = '5'", Map.class);
//        boolean insert = ccMapperUtils.insert("INSERT INTO user(a, b) VALUES ('6', '6');");
//        Map<String, Object> condition = new HashMap<>();
//        condition.put("a", "7");
//        condition.put("b", "7");
//        boolean insert = ccMapperUtils.insert("INSERT INTO user(a, b) VALUES (#{a,jdbcType=VARCHAR}, #{b});", condition);
//        List<Map> select = (List<Map>) ccMapperUtils.select("select * from user where id = '5'", Map.class);
        System.out.println();
        List<Map> select2 = ccMapperUtils.select("select * from user where a = '5'", Map.class);
        System.out.println();

        return resultData;

    }

}
