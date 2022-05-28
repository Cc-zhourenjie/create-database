package com.cc.mybaits.utils;


import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationContext;

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

    private static ApplicationContext applicationContext;

    /**
     * 无参构造方法
     */
    public CcMapperUtils() {
        this.sqlSession = CcMapperUtils.applicationContext.getBean(SqlSession.class);
        builderConfiguration(this.sqlSession.getConfiguration());
    }

    /**
     * 传入SqlSession
     */
    public CcMapperUtils(SqlSession sqlSession) {
        builderConfiguration(sqlSession.getConfiguration());
        this.sqlSession = sqlSession;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        CcMapperUtils.applicationContext = applicationContext;
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
        //构建msId
        String msId = createMsId(condition.getClass() + sql, SqlCommandType.INSERT);
        if (hashMappedStatement(msId)) {
            return msId;
        }
        //通过MyBatis识别语言服务做sql中的参数替换和标签处理
        SqlSource staticSqlSource = languageDriver.createSqlSource(this.configuration, sql, condition.getClass());
        //构建插入的Statement
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
        msId.append("." + sql.hashCode());
        return msId.toString();
    }

    /**
     * 构建查询的Statement
     *
     * @param key
     * @param sqlSource
     * @param resultType
     */
    private void buildSelectMappedStatement(String key, SqlSource sqlSource, final Class<?> resultType) {
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
    private void buildInsertMappedStatement(String key, SqlSource sqlSource) {
        MappedStatement mappedStatement = new MappedStatement.Builder(configuration, key, sqlSource, SqlCommandType.INSERT)
                .resultMaps(new ArrayList<ResultMap>() {{
                    add(new ResultMap.Builder(configuration, defaultResultMapId, Boolean.class, new ArrayList<ResultMapping>(0)).build());
                }}).build();
        configuration.addMappedStatement(mappedStatement);
    }


//手动处理参数替换机制
//    private String insertReturnMsId(String sql, Map<String, Object> condition) {
    //如果没有传入参数就不做参数替换处理
//        List<ParameterMapping> parameterMappings = null;
//        StaticSqlSource staticSqlSource = null;
//        if (MapUtils.isNotEmpty(condition)) {
//            /**
//             * 这段逻辑等待封装成参数替换方法
//             */
    //替换${}的方法
//            Properties properties = new Properties();
//            condition.forEach((k, v) -> {
//                properties.put(k, v);
//            });
//            //sql：
//            //CREATE TABLE ceshi(id varchar(50) PRIMARY KEY NOT NULL COMMENT #{field_comment}) COMMENT = #{table_comment};
//            sql = PropertyParser.parse(sql, properties);
//            //自己的替换逻辑，最后生成newSql
////            GenericTokenParser genericTokenParser = new GenericTokenParser("${", "}", new TokenHandler() {
////                @Override
////                public String handleToken(String content) {
////                    //自己的替换逻辑
////                    return "?";
////                }
////            });
////            //newSql：CREATE TABLE null(null null PRIMARY KEY NOT NULL COMMENT #{field_comment} ) COMMENT = #{table_comment};
////            String newSql = genericTokenParser.parse(sql);
//
//            //替换标签方法
////            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder();
    //替换#{}为?方法
//            SqlSourceBuilder sqlSourceBuilder = new SqlSourceBuilder(this.configuration);
//            staticSqlSource = (StaticSqlSource) sqlSourceBuilder.parse(sql, Map.class, condition);
//            BoundSql boundSql = staticSqlSource.getBoundSql(condition);
////            parameterMappings = boundSql.getParameterMappings();
//            sql = boundSql.getSql();
//        }
    //构建msId
//        String msId = createMsId(condition.getClass() + sql, SqlCommandType.INSERT);
//        if (hashMappedStatement(msId)) {
//            return msId;
//        }
//        buildInsertMappedStatement(msId, staticSqlSource);
//        return msId;
//    }


}
