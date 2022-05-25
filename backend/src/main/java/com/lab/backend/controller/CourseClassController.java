package com.lab.backend.controller;

import com.lab.backend.domain.CourseClass;
import com.lab.backend.domain.Teacher;
import com.lab.backend.service.CourseClassService;
import com.lab.backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courseClass")
public class CourseClassController {
    @Resource
    private CourseClassService courseClassService;

    @PostMapping("/insert")
    public Result<CourseClass> insertController(@RequestBody CourseClass courseClass) {
        int result = courseClassService.insert(courseClass);
        if (result == 0) {
            return Result.success(courseClass);
        } else if (result == 1) {
            return Result.error("1", "对应课程不存在，插入失败！");
        } else if (result == 2) {
            return Result.error("2", "对应教师不存在，插入失败！");
        } else {
            return Result.error("3", "当前记录已存在，插入失败！");
        }
    }

    @PostMapping("/update")
    public Result<CourseClass> updateController(@RequestBody CourseClass courseClass) {
        int result = courseClassService.update(courseClass);
        if (result == 0) {
            return Result.success(courseClass);
        } else if (result == 1) {
            return Result.error("1", "对应课程不存在，更新失败！");
        } else if (result == 2) {
            return Result.error("2", "对应教师不存在，更新失败！");
        } else {
            return Result.error("3", "当前记录不存在，更新失败！");
        }
    }

    @PostMapping("/delete")
    public Result<String> deleteController(@RequestParam String courseClassID) {
        int result = courseClassService.delete(courseClassID);
        if (result == 0) {
            return Result.success(courseClassID);
        } else if (result == 1) {
            return Result.error("1", "对应学生选课非空，删除失败！");
        } else {
            return Result.error("2", "该课程班级不存在，删除失败！");
        }
    }

    @GetMapping("/list")
    public Result<List<CourseClass>> listController() {
        List<CourseClass> list = courseClassService.getList();
        if (!list.isEmpty()) {
            return Result.success(list, "列表查看成功！");
        } else {
            return Result.error("1", "当前列表为空！");
        }
    }


    @GetMapping("/query")
    public Result<Map<Object, Object>> queryController(@RequestBody CourseClass courseClass, @RequestParam int pageIndex, @RequestParam int pageSize) {
        Map<Object, Object> response = courseClassService.query(courseClass, pageIndex, pageSize);
        if ((int) response.get("total") != 0) {
            return Result.success(response, "查询成功");
        } else {
            return Result.error("1", "查询结果为空");
        }
    }
}
