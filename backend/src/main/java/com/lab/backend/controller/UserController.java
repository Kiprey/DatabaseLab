package com.lab.backend.controller;

import com.lab.backend.service.UserService;
import com.lab.backend.utils.Result;
import org.springframework.web.bind.annotation.*;

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
    public Map<String, Object> changePassword(@RequestBody Map<String,String> map) {
        if (userService.changePassword(map) == 1) {
            return Result.resultCode("500", "原密码错误");
        } else {
            return Result.resultCode("200", "修改成功");
        }
    }
}
