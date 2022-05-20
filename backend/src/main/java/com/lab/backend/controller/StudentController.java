package com.lab.backend.controller;

import com.lab.backend.domain.Student;
import com.lab.backend.service.StudentService;
import com.lab.backend.utils.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;

    @PostMapping("/insert")
    public Result<Student> insertController(@RequestBody Student student){
        int r=studentService.insert(student);
        if(r==0){
            return Result.success(student);
        }
        else if(r==1){
            return Result.error("1","班级不存在，无法插入！");
        }
        else{
            return Result.error("1","当前记录已存在，插入失败！");
        }
    }
    @PostMapping("/update")
    public Result<Student> deleteController(@RequestBody Student student){
        int r=studentService.update(student);
        if(r==0){
            return Result.success(student);
        }
        else if(r==1){
            return Result.error("1","班级不存在，无法更新！");
        }
        else{
            return Result.error("1","当前记录不存在，无法更新！");
        }
    }
    @PostMapping("/delete")
    public Result<String> updateController(@RequestParam String studentID){
        int r=studentService.delete(studentID);
        if(r==0){
            return Result.success(studentID);
        }
        else{
            return Result.error("1","当前记录不存在，删除失败！");
        }
    }
    @GetMapping("/list")
    public Result<List<Student>> listController(){
        List<Student> list =studentService.getList();
        if(!list.isEmpty()){
            return Result.success(list,"列表查看成功！");
        }else{
            return Result.error("1","当前列表为空！");
        }
    }
    @PostMapping("/query")
    public Result<Map<Object, Object>> queryController(@RequestBody Student student,String className,@RequestParam int pageIndex,@RequestParam int pageSize){
        Map<Object, Object> response =studentService.query(student,className,pageIndex,pageSize);
        if((int)response.get("total")!=0){
            return Result.success(response,"查询成功");
        }else{
            return Result.error("1","查询结果为空");
        }
    }

}
