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
        if (result == 0) {
            return Result.success(teacherID);
        } else if (result == 1) {
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

    @GetMapping("/query")
    public Result<List<Teacher>> queryByNameController(@RequestBody Teacher teacher) {
        List<Teacher> list = teacherService.query(teacher);
        if (!list.isEmpty()) {
            return Result.success(list, "查询成功");
        } else {
            return Result.error("1", "查询失败，结果为空");
        }
    }
}
