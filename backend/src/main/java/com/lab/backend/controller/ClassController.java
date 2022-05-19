package com.lab.backend.controller;

import com.lab.backend.domain.Classes;
import com.lab.backend.service.ClassService;
import com.lab.backend.utils.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

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
    public Result<String> updateController(@RequestParam String classCode){
        int r=classService.delete(classCode);
        if(r==0){
            return Result.success(classCode);
        }
        else if(r==1){
            return Result.error("1","该班级下学生非空，无法删除！");
        }
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
    @PostMapping("/query")
    public Result<Map<Object, Object>> queryController(@RequestBody Classes classes, String majorName,@RequestParam int pageIndex, @RequestParam int pageSize){
        Map<Object, Object> response =classService.query(classes,majorName,pageIndex,pageSize);
        if((int)response.get("total")!=0){
            return Result.success(response,"查询成功");
        }else{
            return Result.error("1","查询结果为空");
        }
    }

}
