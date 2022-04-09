package com.cc.mybaits.utils;


import org.apache.commons.collections4.MapUtils;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通过SqlSession操作数据库工具类方法
 * 封装了创建msId、替换Map参数、构建Statement方法并把msId放入缓存
 * msId结构（类似）：com.cc.mybaits.utils.CcMapperUtils.SELECT.interface java.util.Mapselect * from user
 * 注意：本类不允许对this.sqlSession做close操作，Spring不允许手动关闭Spring管理的Bean
 *
 * @author cc
 */
public class CcMapperUtils {

    /**
     * 默认返回映射结果的Id
     */
    private String defaultResultMapId = "defaultResultMap";

    private String outDefaultResultMapId = "outDefaultResultMap";

    /**
     * mybatis配置，每次从传来的SqlSession获取（后期方便操作多数据源问题）
     */
    private Configuration configuration;

    /**
     * 当前操作数据库的SqlSession
     */
    private SqlSession sqlSession;

    /**
     * 数据库语言
     */
    private LanguageDriver languageDriver;

    /**
     * 无参构造方法
     */
    public CcMapperUtils() {
    }

    /**
     * 传入SqlSession
     */
    public CcMapperUtils(SqlSession sqlSession) {
//        this.configuration = sqlSession.getConfiguration();
//        DefaultConfigBuilder defaultConfigBuilder = new DefaultConfigBuilder();
//        Configuration configuration = defaultConfigBuilder.parseConfiguration();
//        builderConfiguration(configuration);
//        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
//        this.sqlSession = sqlSessionFactory.openSession();
        builderConfiguration(sqlSession.getConfiguration());
        this.sqlSession = sqlSession;
    }

    /**
     * 构建Configuration
     *
     * @param configuration
     */
    private void builderConfiguration(Configuration configuration) {
        this.configuration = configuration;
        this.languageDriver = configuration.getDefaultScriptingLanguageInstance();
        if (!configuration.hasResultMap(outDefaultResultMapId)) {
            ResultMap resultMap = new ResultMap.Builder(configuration, outDefaultResultMapId, Map.class, new ArrayList<ResultMapping>(0)).build();
            configuration.addResultMap(resultMap);
        }
    }

    /**
     * 无条件查询
     *
     * @param sql
     * @param resultType
     * @param <T>
     * @return
     */
    public <T> List<T> select(String sql, Class<T> resultType) {
        String msId = selectReturnMsId(sql, resultType);
        List<T> objects = this.sqlSession.selectList(msId);
        return objects;
    }

    /**
     * 无参插入
     *
     * @param sql
     * @return
     */
    public boolean insert(String sql) {
        String msId = insertReturnMsId(sql, null);
        int insert = this.sqlSession.insert(msId);
        return insert > 0;
    }

    /**
     * 有参插入，需要参数替换 #{} => ?
     *
     * @param sql
     * @param condition
     * @return
     */
    public boolean insert(String sql, Map<String, Object> condition) {
        String msId = insertReturnMsId(sql, condition);
        int insert = this.sqlSession.insert(msId, condition);
        if (insert > 0) {
            this.sqlSession.commit();
            return true;
        }
        return false;
    }

    /**
     * 返回查询的MsId
     *
     * @param sql
     * @param resultType
     * @return
     */
    private String selectReturnMsId(String sql, Class<?> resultType) {
        //构建msId
        String msId = createMsId(resultType + sql, SqlCommandType.SELECT);
        if (hashMappedStatement(msId)) {
            return msId;
        }
        StaticSqlSource staticSqlSource = new StaticSqlSource(configuration, sql);
        buildSelectMappedStatement(msId, staticSqlSource, resultType);
        return msId;
    }

    /**
     * 构建插入
     *
     * @param sql
     * @param condition
     * @return
     */
    private String insertReturnMsId(String sql, Map<String, Object> condition) {
        //如果没有传入参数就不做参数替换处理
        List<ParameterMapping> parameterMappings = null;
        if (MapUtils.isEmpty(condition)) {
            SqlSourceBuilder sqlSourceBuilder = new SqlSourceBuilder(this.configuration);
            SqlSource parse = sqlSourceBuilder.parse(sql, Map.class, condition);
            BoundSql boundSql = parse.getBoundSql(condition);
            parameterMappings = boundSql.getParameterMappings();
            sql = boundSql.getSql();
        }
        //构建msId
        String msId = createMsId(Boolean.class + sql, SqlCommandType.INSERT);
        if (hashMappedStatement(msId)) {
            return msId;
        }
        StaticSqlSource staticSqlSource = new StaticSqlSource(configuration, sql, parameterMappings);
        buildInsertMappedStatement(msId, staticSqlSource);
        return msId;
    }

    /**
     * 判断当前msId是否在configuration中
     *
     * @param msId
     * @return
     */
    private boolean hashMappedStatement(String msId) {
        return configuration.hasStatement(msId, false);
    }

    /**
     * 创建msId
     *
     * @param sql
     * @param sqlCommandType
     * @return
     */
    private String createMsId(String sql, SqlCommandType sqlCommandType) {
        StringBuilder msId = new StringBuilder(getClass().getName() + "." + sqlCommandType.toString());
        msId.append("." + sql);
        return msId.toString();
    }

    /**
     * 构建查询的Statement
     *
     * @param key
     * @param sqlSource
     * @param resultType
     */
    private void buildSelectMappedStatement(final String key, SqlSource sqlSource, final Class<?> resultType) {
        MappedStatement mappedStatement = new MappedStatement.Builder(configuration, key, sqlSource, SqlCommandType.SELECT)
                .resultMaps(new ArrayList<ResultMap>() {{
                    add(new ResultMap.Builder(configuration, defaultResultMapId, resultType, new ArrayList<ResultMapping>(0)).build());
                }}).build();
        configuration.addMappedStatement(mappedStatement);
    }

    /**
     * 构建插入的Statement
     *
     * @param key
     * @param sqlSource
     */
    private void buildInsertMappedStatement(final String key, SqlSource sqlSource) {
        MappedStatement mappedStatement = new MappedStatement.Builder(configuration, key, sqlSource, SqlCommandType.INSERT)
                .resultMaps(new ArrayList<ResultMap>() {{
                    add(new ResultMap.Builder(configuration, defaultResultMapId, Boolean.class, new ArrayList<ResultMapping>(0)).build());
                }}).build();
        configuration.addMappedStatement(mappedStatement);
    }


}
