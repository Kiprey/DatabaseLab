package com.lab.backend.controller;

import com.lab.backend.service.UserService;
import com.lab.backend.utils.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 当前用户修改密码
     */
    @PostMapping("/changePassword")
    public Map<String, Object> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        if (userService.changePassword(oldPassword, newPassword) == 1) {
            return Result.resultCode("500", "原密码错误");
        } else {
            return Result.resultCode("200", "修改成功");
        }
    }
}
