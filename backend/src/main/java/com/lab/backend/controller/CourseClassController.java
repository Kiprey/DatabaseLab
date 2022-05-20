package com.lab.backend.controller;

import com.lab.backend.domain.CourseClass;
import com.lab.backend.service.CourseClassService;
import com.lab.backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/courseClass")
public class CourseClassController {
    @Resource
    private CourseClassService courseClassService;

//    @PostMapping("/insert")
//    public Result<CourseClass> insertController(@RequestBody CourseClass teacher) {
//
//    }
//
//    @PostMapping("/update")
//    public Result<CourseClass> updateController(@RequestBody Teacher teacher) {
//    }

//    @PostMapping("/delete")
//    public Result<String> deleteController(@RequestParam String teacherID) {
//    }

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
    public Result<List<CourseClass>> queryByNameController(@RequestBody CourseClass courseClass) {
        List<CourseClass> list = courseClassService.query(courseClass);
        if (!list.isEmpty()) {
            return Result.success(list, "查询成功");
        } else {
            return Result.error("1", "查询失败，结果为空" );
        }
    }
}
