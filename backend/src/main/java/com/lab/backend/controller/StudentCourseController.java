package com.lab.backend.controller;

import com.lab.backend.domain.StudentCourse;
import com.lab.backend.service.StudentCourseService;
import com.lab.backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/studentcourse")
public class StudentCourseController {
    @Resource
    private StudentCourseService studentCourseService;
    @PostMapping("/insert")
    public Result<StudentCourse> insertController(@RequestBody StudentCourse studentCourse){
        int res_code = studentCourseService.insert(studentCourse);
        if(res_code == 0){
            return Result.success(studentCourse);
        } else if (res_code == 1){
            return Result.error("1","没有该学生，插入失败！");
        } else if (res_code == 2){
            return Result.error("2","没有课程班级，插入失败！");
        } else {
            return Result.error("3","已经有了该选课，插入失败！");
        }
    }
    @PostMapping("/update")
    public Result<StudentCourse> deleteController(@RequestBody StudentCourse studentCourse){
        int res_code = studentCourseService.update(studentCourse);
        if(res_code == 0){
            return Result.success(studentCourse);
        } else if (res_code == 1){
            return Result.error("1","没有找到对应的班级课程，更新失败！");
        } else {
            return Result.error("2","没有找到对应的学生，更新失败！");
        }
    }
    @PostMapping("/delete")
    public Result<String> updateController(@RequestParam String courseClassID,@RequestParam String studentID){
        int res_code = studentCourseService.delete(courseClassID,studentID);
        if(res_code == 0){
            return Result.success(courseClassID+" "+studentID);
        } else if (res_code == 1){
            return Result.error("1","没有找到要删除的班级课程，删除失败！");
        } else {
            return Result.error("2","没有找到对应的学生，删除失败！");
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
    @PostMapping("/query")
    public Result<Map<Object, Object>> queryController(@RequestBody StudentCourse studentCourse, @RequestParam int pageIndex, @RequestParam int pageSize){
        Map<Object, Object> response = studentCourseService.query(studentCourse,pageIndex,pageSize);
        if((int)response.get("total")!=0){
            return Result.success(response,"查询成功");
        }else{
            return Result.error("1","查询结果为空");
        }
    }
    @PostMapping("/mean")
    public Result<List<Integer>> CountMean(@RequestParam String studentID) {
        List<Integer> list = studentCourseService.getMeanScore(studentID);
        if (!list.isEmpty()) {
            return Result.success(list, "平均分查询成功！");
        } else {
            return Result.error("1", "当前列表为空！");
        }
    }
}
