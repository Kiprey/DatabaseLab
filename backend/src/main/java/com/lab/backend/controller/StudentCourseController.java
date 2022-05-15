package com.lab.backend.controller;

import com.lab.backend.domain.StudentCourse;
import com.lab.backend.service.StudentCourseService;
import com.lab.backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/studentcourse")
public class StudentCourseController {
    @Resource
    private StudentCourseService studentCourseService;
    @PostMapping("/insert")
    public Result<StudentCourse> insertController(@RequestBody StudentCourse studentCourse){
        if(studentCourseService.insert(studentCourse)==1){
            return Result.success(studentCourse);
        }
        else{
            return Result.error("1","当前记录已存在，插入失败！");
        }
    }
    @PostMapping("/update")
    public Result<StudentCourse> deleteController(@RequestBody StudentCourse studentCourse){
        if(studentCourseService.update(studentCourse)==1){
            return Result.success(studentCourse);
        }
        else{
            return Result.error("1","当前记录不存在，无法更新！");
        }
    }
    @PostMapping("/delete")
    public Result<String> updateController(@RequestBody String courseID){
        int r=studentCourseService.delete(courseID);
        if(r==0){
            return Result.success(courseID);
        }
        else if(r==1){
            return Result.error("1","该院系下专业非空，无法删除");
        }
        else{
            return Result.error("1","当前记录不存在，删除失败！");
        }
    }
    @GetMapping("/list")
    public Result<List<StudentCourse>> listController() {
        List<StudentCourse> list = studentCourseService.getList();
        if (!list.isEmpty()) {
            return Result.success(list, "列表查看成功！");
        } else {
            return Result.error("1", "当前列表为空！");
        }
    }
    @GetMapping("/query")
    public Result<List<StudentCourse>> queryController(@RequestParam String courseID){
        List<StudentCourse> list =studentCourseService.getByCode(courseID);
        if(!list.isEmpty()){
            return Result.success(list,"查询成功");
        }else{
            return Result.error("1","查询失败，"+courseID+"不存在");
        }
    }
}
