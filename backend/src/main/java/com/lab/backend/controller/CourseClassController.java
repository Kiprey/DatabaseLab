package com.lab.backend.controller;

import com.lab.backend.domain.CourseClass;
import com.lab.backend.service.CourseClassService;
import com.lab.backend.utils.Result;
import com.lab.backend.utils.SecurityUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courseClass")
public class CourseClassController {
    @Resource
    private CourseClassService courseClassService;

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public Result<List<CourseClass>> listController() {
        List<CourseClass> list = courseClassService.getList();
        if (!list.isEmpty()) {
            return Result.success(list, "列表查看成功！");
        } else {
            return Result.error("1", "当前列表为空！");
        }
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/query")
    public Result<Map<Object, Object>> queryController(@RequestBody Map<String,Object> map, @RequestParam int pageIndex, @RequestParam int pageSize) {
        Map<Object, Object> response = courseClassService.query(map, pageIndex, pageSize);
        if ((int) response.get("total") != 0) {
            return Result.success(response, "查询成功");
        } else {
            return Result.error("1", "查询结果为空");
        }
    }
    @PreAuthorize("hasAnyRole('STUDENT','TEACHER')")
    @PostMapping("/queryByUser")
    public Result<Map<Object, Object>> queryByUserController(@RequestBody Map<String,Object> map, @RequestParam int pageIndex, @RequestParam int pageSize) {
        Map<Object, Object> response = courseClassService.queryByUser(map, pageIndex, pageSize);
        if ((int) response.get("total") != 0) {
            return Result.success(response, "查询成功");
        } else {
            return Result.error("1", "查询结果为空");
        }
    }
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/queryByTeacher")
    public Result<Map<Object, Object>> queryByTeacherController(@RequestBody Map<String,Object> map,@RequestParam int pageIndex, @RequestParam int pageSize) {
        map.put("teacherID", SecurityUtil.getUserName());
        Map<Object, Object> response = courseClassService.query(map, pageIndex, pageSize);
        if ((int) response.get("total") != 0) {
            return Result.success(response, "查询成功");
        } else {
            return Result.error("1", "查询结果为空");
        }
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/insertByTeacher")
    public Result<CourseClass> insertByTeacherController(@RequestBody CourseClass courseClass) {
        int result = courseClassService.insertByTeacher(courseClass);
        if (result == 0) {
            return Result.success(courseClass);
        } else if (result == 1) {
            return Result.error("1", "对应课程不存在，插入失败！");
        } else  {
            return Result.error("2", "当前记录已存在，插入失败！");
        }
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/updateByTeacher")
    public Result<CourseClass> updateByTeacherController(@RequestBody CourseClass courseClass) {
        int result = courseClassService.updateByTeacher(courseClass);
        if (result == 0) {
            return Result.success(courseClass);
        } else if (result == 1) {
            return Result.error("1", "对应课程不存在，更新失败！");
        } else  {
            return Result.error("2", "当前记录不存在，更新失败！");
        }
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/deleteByTeacher")
    public Result<String> deleteByTeacherController(@RequestParam String courseClassID) {
        int result = courseClassService.deleteByTeacher(courseClassID);
        if (result == 0) {
            return Result.success(courseClassID);
        } else if (result == 1) {
            return Result.error("1", "对应学生选课非空，删除失败！");
        } else if (result == 2){
            return Result.error("2", "该课程班级不存在，删除失败！");
        }else
        {
            return Result.error("3","该课程不是你开的！");
        }
    }
}
