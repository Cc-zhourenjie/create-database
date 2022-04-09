package com.cc.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBUtil {
    /*private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/stumgr";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";*/

    private static String DRIVER;
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        Properties properties = new Properties();
        try {
            DRIVER = "com.mysql.jdbc.Driver";
            URL = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC";
            USER = "root";
            PASSWORD = "123456";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建数据库对象并返回
     *
     * @return
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("对象名称创建失败");
        } catch (SQLException e) {
            System.out.println("数据库的地址或用户名和密码不正确");
        }
        return conn;
    }

    //测试......
//    public static void main(String[] args) throws Exception {
//        String sql = "select * from emp";
//        show(sql);
//    }

//    public static void show(String sql) throws Exception {
//        //(查询)
//        //(1)获取连接
//        Connection connection = DBUtil.getConnection();
//        //(2)获取SQL语句发送器
//        Statement stmt = connection.createStatement();
//
//        //(4)发送SQL语句到数据库执行
//        ResultSet rs = stmt.executeQuery(sql);
//
//        List<Emp> list = DBUtil.resultSet2List(rs, Emp.class);
//        for (Emp dept : list) {
//            System.out.println("111111");
//            System.out.println(dept);
//        }
//    }


    /**
     * 利用反射封装的查询语言
     *
     * @param rs
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> resultSet2List(ResultSet rs, Class<T> clazz) {
        //声明一个List
        List<T> list = new ArrayList<T>();
        try {
            //获取当前类所有的属性
            Field[] dfields = clazz.getDeclaredFields();
            //首先遍历resultSet
            while (rs.next()) {
                //创建一个对象
                T bean = clazz.getConstructor().newInstance();
                //遍历属性
                for (int i = 0; i < dfields.length; i++) {
                    //获取属性的名字
                    String fieldName = dfields[i].getName();
                    if (!fieldName.equals("serialVersionUID")) {
                        //获取属性对应再数据库中的值
                        Object fieldValue = rs.getObject(fieldName);
                        //获取属性的set方法
                        String setMethod = "set" + fieldName.toUpperCase().substring(0, 1) + fieldName.substring(1);
                        //获取方法
                        Method sMethod = clazz.getMethod(setMethod, dfields[i].getType());
                        System.out.println(fieldName + "===>" + fieldValue + "===>" + dfields[i].getType());

                        if (fieldValue == null) {
                            fieldValue = 0;
                        }
                        //System.out.println("**************"+fieldValue);

                        //要将值设置给对象
                        sMethod.invoke(bean, fieldValue);
                    }
                }
                //然后将对象添加到List
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("不匹配，建立新的查询");
        }

        return list;
    }


    /**
     * 增、删、改数据库信息
     *
     * @param sql
     * @param obj
     * @return
     */
    public static int changeDate(String sql, Object... obj) {
        int result = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++)
                stmt.setObject(i + 1, obj[i]);
            result = stmt.executeUpdate();      //本句话有一个异常
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("数据已存在" + e);

        } finally {
            closeAll(null, conn, stmt);
        }
        return result;
    }


    /**
     * 无条件查询方法
     *
     * @param sql
     * @return
     */
    public static ResultSet queryDate(String sql) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs.next()) {
                return rs;
            } else
                closeAll(rs, conn, stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 有条件查询
     * @param sql
     * @param obj
     * @return
     */
    /*public static ResultSet queryDate(String sql, Object... obj) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++)
                stmt.setObject(i + 1, obj[i]);
            rs = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(null, conn, stmt);
        }
        return result;
    }*/


    /**
     * 关闭所有缓冲
     *
     * @param rs
     * @param conn
     * @param stmt
     */
    public static void closeAll(ResultSet rs, Connection conn, Statement stmt) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
