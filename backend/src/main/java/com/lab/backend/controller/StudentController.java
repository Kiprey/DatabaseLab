package com.lab.backend.controller;

import com.lab.backend.domain.Student;

import com.lab.backend.service.AdminService;
import com.lab.backend.service.StudentService;
import com.lab.backend.utils.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;
    @Resource
    private AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/insert")
    public Result<Student> insertController(@RequestBody Student student){
        int r=studentService.insert(student);
        if(r==0){
            adminService.studentRegister(student.getStudentID(),"123456");
            return Result.success(student);
        }
        else if(r==1){
            return Result.error("1","班级不存在，无法插入！");
        }
        else{
            return Result.error("1","当前记录已存在，插入失败！");
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public Result<Student> updateController(@RequestBody Student student){
        int r=studentService.update(student);
        if(r==0){
            return Result.success(student);
        }
        else if(r==1){
            return Result.error("1","班级不存在，无法更新！");
        }
        else{
            return Result.error("1","当前记录不存在，无法更新！");
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    public Result<String> deleteController(@RequestParam String studentID){
        int r=studentService.delete(studentID);
        if(r==0){
            return Result.success(studentID);
        }
        else{
            return Result.error("1","当前记录不存在，删除失败！");
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/query")
    public Result<Map<Object, Object>> queryController(@RequestBody Student student,String className,String majorName,String facultyName,@RequestParam int pageIndex,@RequestParam int pageSize){
        Map<Object, Object> response =studentService.query(student,className,majorName,facultyName,pageIndex,pageSize);
        if((int)response.get("total")!=0){
            return Result.success(response,"查询成功");
        }else{
            return Result.error("1","查询结果为空");
        }
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/info")
    public Result<List<Map<String, Object>>> infoController() {
        List<Map<String, Object>> response = studentService.getInfo();
        return Result.success(response, "查看成功！");
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/updateByStudent")
    public Result<Student> updateByStudentController(@RequestBody Student student){
        int r=studentService.updateByStudent(student);
        if(r==0){
            return Result.success(student);
        }
        else{
            return Result.error("1","当前记录不存在，无法更新！");
        }
    }
}
