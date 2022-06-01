package com.lab.backend.controller;

import com.lab.backend.domain.Teacher;
import com.lab.backend.service.AdminService;
import com.lab.backend.service.TeacherService;
import com.lab.backend.utils.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Resource
    private TeacherService teacherService;
    private AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/insert")
    public Result<Teacher> insertController(@RequestBody Teacher teacher) {
        int result = teacherService.insert(teacher);
        if (result == 0) {
            adminService.teacherRegister(teacher.getTeacherID(),"123456");
            return Result.success(teacher);
        } else if (result == 1) {
            return Result.error("1", "对应院系不存在，插入失败！");
        } else {
            return Result.error("2", "当前记录已存在，插入失败！");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public Result<Teacher> updateController(@RequestBody Teacher teacher) {
        int result = teacherService.update(teacher);
        if (result == 0) {
            return Result.success(teacher);
        } else if (result == 1) {
            return Result.error("1", "对应院系不存在，更新失败！");
        } else {
            return Result.error("2", "当前记录不存在，更新失败！");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public Result<List<Teacher>> listController() {
        List<Teacher> list = teacherService.getList();
        if (!list.isEmpty()) {
            return Result.success(list, "列表查看成功！");
        } else {
            return Result.error("1", "当前列表为空！");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/query")
    public Result<Map<Object, Object>> queryController(@RequestBody Map<String,Object> map, @RequestParam int pageIndex, @RequestParam int pageSize) {
        Map<Object, Object> response = teacherService.query(map, pageIndex, pageSize);
        if ((int) response.get("total") != 0) {
            return Result.success(response, "查询成功");
        } else {
            return Result.error("1", "查询结果为空");
        }
    }
    @PreAuthorize("hasAnyRole('STUDENT','TEACHER')")
    @PostMapping("/queryWithoutID")
    public Result<Map<Object, Object>> queryWithoutIDController(@RequestBody Map<String,Object> map, @RequestParam int pageIndex, @RequestParam int pageSize) {
        Map<Object, Object> response = teacherService.queryWithoutID(map, pageIndex, pageSize);
        if ((int) response.get("total") != 0) {
            return Result.success(response, "查询成功");
        } else {
            return Result.error("1", "查询结果为空");
        }
    }
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/info")
    public Result<List<Teacher>> infoController() {
        List<Teacher> list = teacherService.info();
        return Result.success(list, "查看成功！");
    }


}
