package com.lab.backend.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.Servlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class Result<T> {
    private String code;
    private String message;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode("0");
        result.setMessage("成功");
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(data);
        result.setCode("0");
        result.setMessage("成功");
        return result;
    }

    public static <T> Result<T> success(T data,String message) {
        Result<T> result = new Result<>(data);
        result.setCode("0");
        result.setMessage(message);
        return result;
    }
    public static <T> Result<T> error(String code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    /**
     * 使用response输出JSON
     * @Author Sans
     * @CreateTime 2019/9/28 11:23
     * @Param  resultMap 数据
     * @Return void
     */
    public static void responseJson(ServletResponse response, Map<String, Object> resultMap) throws IOException {
        ServletOutputStream out = response.getOutputStream();
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out.write(JSON.toJSONString(resultMap).getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("【JSON输出异常】"+e);
        }finally{
            if(out!=null){
                out.flush();
                out.close();
            }
        }
    }
    /**
     * 通用示例
     * @Author Sans
     * @CreateTime 2019/9/28 11:35
     * @Param  code 信息码
     * @Param  msg  信息
     * @Return Map<String,Object> 返回数据MAP
     */
    public static Map<String, Object> resultCode(String code,String msg){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message",msg);
        resultMap.put("code",code);
        return resultMap;
    }
}
