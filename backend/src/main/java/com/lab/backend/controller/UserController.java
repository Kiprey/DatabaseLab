package com.lab.backend.controller;


import com.lab.backend.domain.User;
import com.lab.backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private com.lab.backend.service.userSersive userSersive;

    @PostMapping("/delete")
    public Result<User> deleteController(@RequestParam("username") String username){
        User user= userSersive.delete(username);
        if(user!=null){
            return Result.success(user,"用户"+username+"删除成功");
        }
        else {
            return Result.error("1","删除失败，用户"+username+"不存在");
        }

    }

    @GetMapping("/list")
    public Result<List<User>> listController(){
        List<User> list =userSersive.getList();
        if(!list.isEmpty()){
            return Result.success(list,"user表查看成功！");
        }else{
            return Result.error("1","user表查看失败！");
        }
    }
    @GetMapping("/query")
    public Result<List<User>> queryController(@RequestParam String username){
        List<User> list =userSersive.getByUsername(username);
        if(!list.isEmpty()){
            return Result.success(list,"user表查询成功！");
        }else{
            return Result.error("1","user表查询失败！");
        }
    }

}
