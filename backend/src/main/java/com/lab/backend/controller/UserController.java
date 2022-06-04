package com.lab.backend.controller;

import com.lab.backend.service.UserService;
import com.lab.backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    /**
     *  需要登录时返回http状态码401，由前端重定向至登录页面
     */
    @PostMapping("/loginRequire")
    public void loginRequire(HttpServletResponse response) throws IOException {
        Result.responseJson(response,Result.resultCode("401","未登录"));
        response.setStatus(401);
    }

}
