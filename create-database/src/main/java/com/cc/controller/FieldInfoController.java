package com.cc.controller;

import com.cc.pojo.vo.FieldInfoVO;
import com.cc.service.FieldInfoService;
import com.cc.utils.CcString;
import com.cc.utils.ResultData;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 物理表字段控制器
 *
 * @author zrj
 */
@RequestMapping("/table/field")
@RestController
public class FieldInfoController {

    private static final Logger lg = LoggerFactory.getLogger(FieldInfoController.class);

    @Autowired
    private FieldInfoService fieldInfoService;

    @Autowired
    private SqlSession sqlSession;


    /**
     * 创建物理表字段数据
     *
     * @param fieldInfoVO
     * @return
     */
    @PostMapping("/createfield")
    public ResultData createField(@RequestBody FieldInfoVO fieldInfoVO) {
        ResultData resultData = ResultData.init();
        try {
            if (fieldInfoVO == null || CcString.isEmpty(fieldInfoVO.getFieldName())) {
                ResultData.failedMsg("传入字段数据为空！");
            }
            Boolean flag = fieldInfoService.createField(fieldInfoVO);
            if (!flag) ResultData.failedMsg("创建表字段异常！");
        } catch (Exception e) {
            lg.error("创建表字段异常", e);
        }
        return resultData;
    }


}
