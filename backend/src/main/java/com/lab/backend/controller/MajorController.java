package com.lab.backend.controller;

import com.lab.backend.domain.Major;
import com.lab.backend.service.MajorService;
import com.lab.backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/major")
public class MajorController {
    @Resource
    private MajorService majorService;

    @PostMapping("/insert")
    public Result<Major> insertController(@RequestBody Major major){
        int r=majorService.insert(major);
        if(r==0){
            return Result.success(major);
        }
        else if(r==1){
            return Result.error("1","院系不存在，无法插入！");
        }
        else{
            return Result.error("1","当前记录已存在，插入失败！");
        }
    }
    @PostMapping("/update")
    public Result<Major> deleteController(@RequestBody Major major){
        int r=majorService.update(major);
        if(r==0){
            return Result.success(major);
        }
        else if(r==1){
            return Result.error("1","院系不存在，无法更新！");
        }
        else{
            return Result.error("1","当前记录不存在，无法更新！");
        }
    }
    @PostMapping("/delete")
    public Result<String> updateController(@RequestParam String majorCode){
        int r=majorService.delete(majorCode);
        if(r==0){
            return Result.success(majorCode);
        }
        else if(r==1){
            return Result.error("1","该专业下班级非空，无法删除！");
        }
        else{
            return Result.error("1","当前记录不存在，删除失败！");
        }
    }
    @GetMapping("/list")
    public Result<List<Major>> listController(){
        List<Major> list =majorService.getList();
        if(!list.isEmpty()){
            return Result.success(list,"列表查看成功！");
        }else{
            return Result.error("1","当前列表为空！");
        }
    }
    @GetMapping("/query")
    public Result<Map<Object, Object>> queryController(@RequestBody Major major, @RequestParam int pageIndex, @RequestParam int pageSize){
        Map<Object, Object> response =majorService.query(major,pageIndex,pageSize);
        if((int)response.get("total")!=0){
            return Result.success(response,"查询成功");
        }else{
            return Result.error("1","查询结果为空");
        }
    }
    @GetMapping("/queryFaculty")
    public Result<List<Major>> queryFacultyController(@RequestParam String facultyName){
        List<Major> list =majorService.getListByFacultyName(facultyName);
        if(!list.isEmpty()){
            return Result.success(list,"查询成功");
        }else{
            return Result.error("1","查询失败，"+facultyName+"不存在");
        }
    }
}
