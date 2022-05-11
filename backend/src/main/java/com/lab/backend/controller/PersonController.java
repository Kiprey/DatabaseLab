package com.lab.backend.controller;

import com.lab.backend.domain.Person;
import com.lab.backend.service.PersonService;
import com.lab.backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Resource
    private PersonService personService;

    @PostMapping("/add")
    public Result<Person> addController(@RequestBody Person person){
        int t= personService.insert(person);
        if(t == 0){
            return Result.success(person,"username:"+person.getUsername()+ "已存在person表和user表，更新person表数据");
        }
        else if(t==1){
            return Result.success(person,"username:"+person.getUsername()+ "已存在users表，不存在person表，插入person表");
        }
        else if(t==2){
            return Result.success(person,"username:"+person.getUsername()+ "不存在user表，存在person表，插入user表，更新person表数据");
        }
        else if(t==3){
            return Result.success(person,"username:"+person.getUsername()+ "不存在users表和person表，插入user表和person表");
        }
        else{
            return Result.error("1","插入失败！");
        }
    }

    @GetMapping("/list")
    public Result<List<Person>> listController(){
        List<Person> list =personService.getList();
        if(!list.isEmpty()){
            return Result.success(list,"person表查看成功！");
        }else{
            return Result.error("1","person表查看失败！");
        }
    }
    @GetMapping("/query")
    public Result<List<Person>> queryController(@RequestParam String username){
        List<Person> list =personService.getByUsername(username);
        if(!list.isEmpty()){
            return Result.success(list,"person表查找成功！");
        }else{
            return Result.error("1","person表查找失败！");
        }
    }
}
