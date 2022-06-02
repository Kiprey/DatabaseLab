package com.lab.backend.controller;

import com.lab.backend.domain.StudentCourse;
import com.lab.backend.service.StudentCourseService;
import com.lab.backend.utils.Result;
import com.lab.backend.utils.SecurityUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/studentcourse")
public class StudentCourseController {
    @Resource
    private StudentCourseService studentCourseService;
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public Result<StudentCourse> updateController(@RequestBody StudentCourse studentCourse){
        int res_code = studentCourseService.update(studentCourse);
        if(res_code == 0){
            return Result.success(studentCourse);
        } else if (res_code == 1){
            return Result.error("1","没有找到对应的班级课程，更新失败！");
        } else {
            return Result.error("2","没有找到对应的学生，更新失败！");
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    public Result<String> deleteController(@RequestParam String courseClassID,@RequestParam String studentID){
        int res_code = studentCourseService.delete(courseClassID,studentID);
        if(res_code == 0){
            return Result.success(courseClassID+" "+studentID);
        } else if (res_code == 1){
            return Result.error("1","没有找到要删除的班级课程，删除失败！");
        } else {
            return Result.error("2","没有找到对应的学生，删除失败！");
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public Result<List<StudentCourse>> listController() {
        List<StudentCourse> list = studentCourseService.getList();
        if (!list.isEmpty()) {
            return Result.success(list, "列表查看成功！");
        } else {
            return Result.error("1", "当前列表为空！");
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/query")
    public Result<Map<Object, Object>> queryController(@RequestBody Map<String,Object> map, @RequestParam int pageIndex, @RequestParam int pageSize){
        Map<Object, Object> response = studentCourseService.query(map,pageIndex,pageSize);
        if((int)response.get("total")!=0){
            return Result.success(response,"查询成功");
        }else{
            return Result.error("1","查询结果为空");
        }
    }
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/queryByTeacher")
    public Result<Map<Object, Object>> queryByTeacherController(@RequestBody Map<String,Object> map, @RequestParam int pageIndex, @RequestParam int pageSize){
        Map<Object, Object> response = studentCourseService.queryByTeacher(map,pageIndex,pageSize);
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
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/insertByStudent")
    public Result<String> insertByStudentController(@RequestParam String courseClassID){
        int res_code = studentCourseService.insertByStudent(courseClassID);
        if(res_code == 0){
            return Result.success(courseClassID+" "+ SecurityUtil.getUserName());
        } else if (res_code == 1){
            return Result.error("1","没有该学生，插入失败！");
        } else if (res_code == 2){
            return Result.error("2","没有课程班级，插入失败！");
        } else {
            return Result.error("3","已经有了该选课，插入失败！");
        }
    }
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/deleteByStudent")
    public Result<String> deleteByStudentController(@RequestParam String courseClassID){
        int res_code = studentCourseService.deleteByStudent(courseClassID);
        if(res_code == 0){
            return Result.success(courseClassID+" "+ SecurityUtil.getUserName());
        } else if (res_code == 1){
            return Result.error("1","没有找到要删除的班级课程，删除失败！");
        } else {
            return Result.error("2","没有找到对应的学生，删除失败！");
        }
    }
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/updateByTeacher")
    public Result<StudentCourse> updateByTeacherController(@RequestBody StudentCourse studentCourse){
        int res_code = studentCourseService.updateByTeacher(studentCourse);
        if(res_code == 0){
            return Result.success(studentCourse);
        } else if (res_code == 1){
            return Result.error("1","没有找到对应的选课，更新失败！");
        } else {
            return Result.error("2","没权限");
        }
    }
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/queryByStudent")
    public Result<Map<Object, Object>> queryByStudentController(@RequestBody Map<String,Object> map, @RequestParam int pageIndex, @RequestParam int pageSize){
        Map<Object, Object> response = studentCourseService.queryByStudent(map,pageIndex,pageSize);
        if((int)response.get("total")!=0){
            return Result.success(response,"查询成功");
        }else{
            return Result.error("1","查询结果为空");
        }
    }

}
