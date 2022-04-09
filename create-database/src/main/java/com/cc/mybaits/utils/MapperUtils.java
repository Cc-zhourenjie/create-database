package com.cc.mybaits.utils;

import org.apache.ibatis.session.SqlSession;

/**
 * 对外开放自定义sql操作数据库方法，内部封装工具类做具体操作
 *
 * @author cc
 */
public class MapperUtils extends CcMapperUtils {

    /**
     * 当前线程的工具类对象，后期封装为ThreadLocal
     */
    private CcMapperUtils mapperUtils;


    public MapperUtils(SqlSession sqlSession) {
//        CcMapperUtils mapperUtils = new CcMapperUtils(sqlSession.getConfiguration());
        this.mapperUtils = new CcMapperUtils(sqlSession);
    }
}
