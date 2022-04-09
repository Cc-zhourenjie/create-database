package com.cc.utils;

import java.io.Serializable;

public class ResultData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回数据
     */
    private Object data;

    public static ResultData init() {
        return new ResultData() {{
            setCode("200");
            setMessage("成功");
        }};
    }

    /**
     * 请求成功
     *
     * @param data
     * @return
     */
    public static ResultData success(final Object data) {
        return new ResultData() {{
            setCode("200");
            setMessage("成功");
            setData(data);
        }};
    }

    /**
     * 请求失败
     *
     * @return
     */
    public static ResultData failed() {
        return new ResultData() {{
            setCode("-1");
            setMessage("请求失败！");
        }};
    }

    /**
     * 请求失败，自定义提示信息
     *
     * @param message
     * @return
     */
    public static ResultData failedMsg(final String message) {
        return new ResultData() {{
            setCode("-1");
            setMessage(message);
        }};
    }

    /**
     * 请求失败，自定义错误码
     *
     * @param code
     * @param message
     * @return
     */
    public static ResultData failedAll(final String code, final String message) {
        return new ResultData() {{
            setCode(code);
            setMessage(message);
        }};
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
