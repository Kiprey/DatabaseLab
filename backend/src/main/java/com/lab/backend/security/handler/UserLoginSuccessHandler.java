package com.lab.backend.security.handler;

import com.lab.backend.config.JWTConfig;
import com.lab.backend.security.entity.SelfUserEntity;
import com.lab.backend.utils.JWTTokenUtil;
import com.lab.backend.utils.Result;
import com.lab.backend.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.lab.backend.utils.SecurityUtil.getUserInfo;

/**
 * 登录成功处理类
 */
@Slf4j
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * 登录成功返回结果
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 组装JWT
        SelfUserEntity selfUserEntity =  (SelfUserEntity) authentication.getPrincipal();
        String token = JWTTokenUtil.createAccessToken(selfUserEntity);
        token = JWTConfig.tokenPrefix + token;
        // 封装返回参数
        Map<String,Object> resultData = new HashMap<>();
        resultData.put("code","200");
        resultData.put("message", "登录成功");
        Map<String, Object> data = new HashMap<>();
        data.put("token",token);
        data.put("role", SecurityUtil.getUserRole());
        resultData.put("data",data);
        Result.responseJson(response,resultData);
    }
}