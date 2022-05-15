package com.lab.backend.controller;

import com.lab.backend.domain.Course;
import com.lab.backend.service.CourseService;
import com.lab.backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    private CourseService courseService;
    @PostMapping("/insert")
    public Result<Course> insertController(@RequestBody Course course){
        if(courseService.insert(course)==0){
            return Result.success(course);
        }
        else{
            return Result.error("1","当前记录已存在，插入失败！");
        }
    }
    @PostMapping("/update")
    public Result<Course> deleteController(@RequestBody Course course){
        if(courseService.update(course)==0){
            return Result.success(course);
        }
        else{
            return Result.error("1","当前记录不存在，无法更新！");
        }
    }
    @PostMapping("/delete")
    public Result<String> updateController(@RequestParam String courseID){
        int r=courseService.delete(courseID);
        if(r==1){
            return Result.success(courseID);
        }
        else if(r==0){
            return Result.error("0","记录不存在");
        }
        else{
            return Result.error("0","当前记录不存在，删除失败！");
        }
    }
    @GetMapping("/list")
    public Result<List<Course>> listController() {
        List<Course> list = courseService.getList();
        if (!list.isEmpty()) {
            return Result.success(list, "列表查看成功！");
        } else {
            return Result.error("1", "当前列表为空！");
        }
    }
    @GetMapping("/query")
    public Result<List<Course>> queryController(@RequestParam String courseID){
        List<Course> list =courseService.getByCode(courseID);
        if(!list.isEmpty()){
            return Result.success(list,"查询成功");
        }else{
            return Result.error("1","查询失败，"+courseID+"不存在");
        }
    }
}
