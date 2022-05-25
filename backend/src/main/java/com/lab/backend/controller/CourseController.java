package com.lab.backend.controller;

import com.lab.backend.domain.Course;
import com.lab.backend.domain.Faculty;
import com.lab.backend.service.CourseService;
import com.lab.backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    private CourseService courseService;
    @PostMapping("/insert")
    public Result<Course> insertController(@RequestBody Course course){
        int res_code = courseService.insert(course);
        if(res_code == 0){
            return Result.success(course);
        } else if (res_code == 1){
            return Result.error("1","院系不存在，插入失败！");
        } else {
            return Result.error("2","该课程已存在，插入失败！");
        }
    }
    @PostMapping("/update")
    public Result<Course> deleteController(@RequestBody Course course){
        int res_code = courseService.update(course);
        if(res_code == 0){
            return Result.success(course);
        } else if (res_code == 1){
            return Result.error("1","院系不存在，更新失败！");
        } else {
            return Result.error("2","该课程不存在，更新失败！");
        }
    }
    @PostMapping("/delete")
    public Result<String> updateController(@RequestParam String courseID){
        int res_code =courseService.delete(courseID);
        if(res_code == 0){
            return Result.success(courseID);
        } else {
            return Result.error("1","没有所要删除的课程，删除失败！");
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
    @PostMapping("/query")
    public Result<Map<Object, Object>> queryController(@RequestBody Course course, @RequestParam int pageIndex, @RequestParam int pageSize){
        Map<Object, Object> response = courseService.query(course,pageIndex,pageSize);
        if((int)response.get("total")!=0){
            return Result.success(response,"查询成功");
        }else{
            return Result.error("1","查询结果为空");
        }
    }
}
