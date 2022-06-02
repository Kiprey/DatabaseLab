package com.lab.backend.controller;

import com.lab.backend.domain.Major;
import com.lab.backend.service.MajorService;
import com.lab.backend.utils.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/major")
public class MajorController {
    @Resource
    private MajorService majorService;
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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

    @PostMapping("/query")
    public Result<Map<Object, Object>> queryController(@RequestBody Major major,String facultyName,@RequestParam int pageIndex, @RequestParam int pageSize){
        Map<Object, Object> response =majorService.query(major,facultyName,pageIndex,pageSize);
        if((int)response.get("total")!=0){
            return Result.success(response,"查询成功");
        }else{
            return Result.error("1","查询结果为空");
        }
    }

}
