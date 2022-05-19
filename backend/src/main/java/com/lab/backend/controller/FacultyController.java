package com.lab.backend.controller;

import com.lab.backend.domain.Faculty;
import com.lab.backend.service.FacultyService;
import com.lab.backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    @Resource
    private FacultyService facultyService;

    @PostMapping("/insert")
    public Result<Faculty> insertController(@RequestBody Faculty faculty){
        if(facultyService.insert(faculty)){
            return Result.success(faculty);
        }
        else{
            return Result.error("1","当前记录已存在，插入失败！");
        }
    }
    @PostMapping("/update")
    public Result<Faculty> deleteController(@RequestBody Faculty faculty){
        if(facultyService.update(faculty)){
            return Result.success(faculty);
        }
        else{
            return Result.error("1","当前记录不存在，无法更新！");
        }
    }
    @PostMapping("/delete")
    public Result<String> updateController(@RequestParam String facultyCode){
        int r=facultyService.delete(facultyCode);
        if(r==0){
            return Result.success(facultyCode);
        }
        else if(r==1){
            return Result.error("1","该院系下专业非空，无法删除");
        }
        else{
            return Result.error("1","当前记录不存在，删除失败！");
        }
    }

    @PostMapping("/query")
    public Result<Map<Object, Object>> queryController(@RequestBody Faculty faculty, @RequestParam int pageIndex, @RequestParam int pageSize){
        Map<Object, Object> response =facultyService.query(faculty,pageIndex,pageSize);
        if((int)response.get("total")!=0){
            return Result.success(response,"查询成功");
        }else{
            return Result.error("1","查询结果为空");
        }
    }

    @GetMapping("/list")
    public Result<List<Faculty>> listController(){
        List<Faculty> list =facultyService.getList();
        if(!list.isEmpty()){
            return Result.success(list,"列表查看成功！");
        }else{
            return Result.error("1","当前列表为空！");
        }
    }

}
