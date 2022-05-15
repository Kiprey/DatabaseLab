package com.lab.backend.controller;

import com.lab.backend.domain.Classes;
import com.lab.backend.service.ClassService;

import com.lab.backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/class")
public class ClassController {
    @Resource
    private ClassService classService;

    @PostMapping("/insert")
    public Result<Classes> insertController(@RequestBody Classes classes){
        int r=classService.insert(classes);
        if(r==0){
            return Result.success(classes);
        }
        else if(r==1){
            return Result.error("1","对应专业不存在，无法插入！");
        }
        else{
            return Result.error("1","当前记录已存在，插入失败！");
        }
    }
    @PostMapping("/update")
    public Result<Classes> deleteController(@RequestBody Classes classes){
        int r=classService.update(classes);
        if(r==0){
            return Result.success(classes);
        }
        else if(r==1){
            return Result.error("1","对应专业不存在，无法更新！");
        }
        else{
            return Result.error("1","当前记录不存在，无法更新！");
        }
    }
    @PostMapping("/delete")
    public Result<String> updateController(@RequestParam String className){
        int r=classService.delete(className);
        if(r==0){
            return Result.success(className);
        }
//        else if(r==1){
//            return Result.error("1","该专业下班级非空，无法删除！");
//        }
        else{
            return Result.error("1","当前记录不存在，删除失败！");
        }
    }
    @GetMapping("/list")
    public Result<List<Classes>> listController(){
        List<Classes> list =classService.getList();
        if(!list.isEmpty()){
            return Result.success(list,"列表查看成功！");
        }else{
            return Result.error("1","当前列表为空！");
        }
    }
    @GetMapping("/query")
    public Result<List<Classes>> queryController(@RequestParam String className){
        List<Classes> list =classService.getListByName(className);
        if(!list.isEmpty()){
            return Result.success(list,"查询成功");
        }else{
            return Result.error("1","查询失败，"+className+"不存在");
        }
    }
    @GetMapping("/queryMajor")
    public Result<List<Classes>> queryFacultyController(@RequestParam String majorName){
        List<Classes> list =classService.getListByMajorName(majorName);
        if(!list.isEmpty()){
            return Result.success(list,"查询成功");
        }else{
            return Result.error("1","查询失败，"+majorName+"不存在");
        }
    }
}
