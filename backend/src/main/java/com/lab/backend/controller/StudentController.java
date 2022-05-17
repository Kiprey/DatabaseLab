package com.lab.backend.controller;


import com.lab.backend.domain.Student;
import com.lab.backend.service.StudentService;
import com.lab.backend.utils.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

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
//        else if(r==1){
//            return Result.error("1","该专业下班级非空，无法删除！");
//        }
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
    @GetMapping("/query")
    public Result<List<Student>> queryController(@RequestParam String studentName){
        List<Student> list =studentService.getListByName(studentName);
        if(!list.isEmpty()){
            return Result.success(list,"查询成功");
        }else{
            return Result.error("1","查询失败，"+studentName+"不存在");
        }
    }
    @GetMapping("/queryClass")
    public Result<List<Student>> queryFacultyController(@RequestParam String className){
        List<Student> list =studentService.getListByClassName(className);
        if(!list.isEmpty()){
            return Result.success(list,"查询成功");
        }else{
            return Result.error("1","查询失败，"+className+"不存在");
        }
    }
}
