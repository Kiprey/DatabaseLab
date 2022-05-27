package com.lab.backend.controller;

import com.lab.backend.service.AdminService;
import com.lab.backend.utils.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;

    /**
     * 注册管理员用户
     */
    @PostMapping("/adminRegister")
    public Map<String, Object> adminRegister(@RequestParam String username, @RequestParam String password, @RequestParam String code) {
        if (adminService.adminRegister(username, password, code) == 1) {
            return Result.resultCode("500", "邀请码不正确");
        } else {
            return Result.resultCode("200", "注册成功");
        }
    }

    /**
     * 注册普通用户
     * 仅限管理员使用
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/userRegister")
    public Map<String, Object> userRegister(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        int res = adminService.userRegister(username, password, role);
        if (res == 0) {
            return Result.resultCode("200", "注册成功");
        } else if (res == 1)
        {
            return Result.resultCode("1", "该学生不存在");
        }
        else if(res==2)
        {
            return Result.resultCode("2","该教师不存在");
        }
        else if(res==3)
        {
            return Result.resultCode("3","该用户名已存在");
        }
        else
        {
            return Result.resultCode("4","不存在该角色");
        }
    }
}
