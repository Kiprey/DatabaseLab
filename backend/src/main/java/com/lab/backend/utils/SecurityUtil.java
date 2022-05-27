package com.lab.backend.utils;

import com.lab.backend.security.entity.SelfUserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * Security工具类
 * @Author Sans
 * @CreateTime 2019/10/2 13:16
 */
public class SecurityUtil {

    /**
     * 私有化构造器
     */
    private SecurityUtil(){}

    /**
     * 获取当前用户信息
     */
    public static SelfUserEntity getUserInfo(){
        SelfUserEntity userDetails = (SelfUserEntity) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        return userDetails;
    }
    /**
     * 获取当前用户ID
     */
    public static Long getUserId(){
        return getUserInfo().getUserId();
    }
    /**
     * 获取当前用户名
     */
    public static String getUserName(){
        return getUserInfo().getUsername();
    }
    /**
     * 获取当前用户角色
     */
    public static Collection<GrantedAuthority> getUserRole(){
        return getUserInfo().getAuthorities();
    }
}