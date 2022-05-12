package com.lab.backend.controller;

import com.lab.backend.domain.Faculty;
import com.lab.backend.service.FacultyService;
import com.lab.backend.utils.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    @Resource
    private FacultyService facultyService;

    @PostMapping("/insert")
    public Result<Faculty> addController(@RequestBody Faculty faculty){
        int t= facultyService.insert(faculty);
        if(t == 0){
            return Result.success(faculty);
        }
        else{
            return Result.error("1","插入失败！");
        }
    }
}
