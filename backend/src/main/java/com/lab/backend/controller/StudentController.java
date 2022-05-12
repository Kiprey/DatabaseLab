package com.lab.backend.controller;

import com.lab.backend.domain.Student;

import com.lab.backend.service.StudentService;
import com.lab.backend.utils.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;

    @PostMapping("/insert")
    public Result<Student> addController(@RequestBody Student student){
        int t= studentService.insert(student);
        if(t == 0){
            return Result.success(student);
        }
        else{
            return Result.error("1","插入失败！");
        }
    }
}
