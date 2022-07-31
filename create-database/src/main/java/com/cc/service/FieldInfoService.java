package com.cc.service;

import com.cc.pojo.vo.FieldInfoVO;

/**
 * 操作物理表字段服务
 */
public interface FieldInfoService {

    /**
     * 创建物理表字段数据
     *
     * @param fieldInfoVO
     * @return
     */
    Boolean createField(FieldInfoVO fieldInfoVO);


}
