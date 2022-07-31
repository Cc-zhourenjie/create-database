package com.cc.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Json对应转换方法
 *
 * @author cc
 */
public class CcJson {

    // 定义jackson对象
//    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将任意对对象转换成json字符串
     *
     * @param data
     * @return
     */
    public static String objectToJson(Object data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(data);
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> T jsonToBean(String jsonData, Class<T> beanType) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonData, beanType);
    }

    /**
     * 将json数据转换成pojo对象list
     *
     * @param jsonData
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToListBean(String jsonData, Class<T> beanType) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
        List<T> list = mapper.readValue(jsonData, javaType);
        return list;
    }

    /**
     * json字符串转Map
     *
     * @param jsonData
     * @return
     * @throws IOException
     */
    public static Map<String, Object> convertMap(String jsonData) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = mapper.readValue(jsonData, Map.class);
        return result;
    }

}
