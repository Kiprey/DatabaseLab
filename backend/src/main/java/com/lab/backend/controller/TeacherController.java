package com.lab.backend.controller;

import com.lab.backend.domain.Teacher;
import com.lab.backend.service.TeacherService;
import com.lab.backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Resource
    private TeacherService teacherService;

    @PostMapping("/insert")
    public Result<Teacher> insertController(@RequestBody Teacher teacher) {
        int result = teacherService.insert(teacher);
        if (result==0) {
            return Result.success(teacher);
        } else if(result==1){
            return Result.error("1", "对应院系不存在，插入失败！");
        }
        else {
            return Result.error("2", "当前记录已存在，插入失败！");
        }
    }

    @PostMapping("/update")
    public Result<Teacher> updateController(@RequestBody Teacher teacher) {
        int result = teacherService.update(teacher);
        if (result==0) {
            return Result.success(teacher);
        } else if(result==1){
            return Result.error("1", "对应院系不存在，更新失败！");
        }
        else {
            return Result.error("2", "当前记录不存在，更新失败！");
        }
    }

    @PostMapping("/delete")
    public Result<String> deleteController(@RequestParam String teacherID) {
        int result = teacherService.delete(teacherID);
        if (result == 1) {
            return Result.success(teacherID);
        } else if (result == 2) {
            return Result.error("1", "对应课程班级非空，删除失败！");
        } else {
            return Result.error("2", "当前教师不存在，删除失败！");
        }
    }

    @GetMapping("/list")
    public Result<List<Teacher>> listController() {
        List<Teacher> list = teacherService.getList();
        if (!list.isEmpty()) {
            return Result.success(list, "列表查看成功！");
        } else {
            return Result.error("1", "当前列表为空！");
        }
    }

    @GetMapping("/queryByName")
    public Result<List<Teacher>> queryByNameController(@RequestParam String teacherName) {
        List<Teacher> list = teacherService.getByName(teacherName);
        if (!list.isEmpty()) {
            return Result.success(list, "查询成功");
        } else {
            return Result.error("1", "查询失败，" + teacherName + "不存在");
        }
    }

    @GetMapping("/queryByID")
    public Result<List<Teacher>> queryByIDController(@RequestParam String teacherID) {
        List<Teacher> list = teacherService.getByID(teacherID);
        if (!list.isEmpty()) {
            return Result.success(list, "查询成功");
        } else {
            return Result.error("1", "查询失败，" + teacherID + "不存在");
        }
    }

    @GetMapping("/queryByFaculty")
    public Result<List<Teacher>> queryByFacultyController(@RequestParam String facultyCode) {
        List<Teacher> list = teacherService.getByFaculty(facultyCode);
        if (!list.isEmpty()) {
            return Result.success(list, "查询成功");
        } else {
            return Result.error("1", "查询失败，院系" + facultyCode + "不存在");
        }
    }
}
