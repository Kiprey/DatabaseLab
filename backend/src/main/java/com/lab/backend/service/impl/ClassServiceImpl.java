package com.lab.backend.service.impl;

import com.lab.backend.domain.Classes;
import com.lab.backend.domain.Major;
import com.lab.backend.repository.ClassDao;
import com.lab.backend.repository.MajorDao;
import com.lab.backend.repository.StudentDao;
import com.lab.backend.service.ClassService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassServiceImpl implements ClassService {
    @Resource
    private ClassDao classDao;
    @Resource
    private MajorDao majorDao;
    @Resource
    private StudentDao studentDao;
    /**
     * 插入
     * @param classes 班级实体
     * @return 执行结果
     */
    @Override
    public int insert(Classes classes) {
        int num=classDao.getByCode(classes.getClassCode()).size();
        int majorNum=majorDao.getByCode(classes.getMajorCode()).size();
        if(num==0){
            if(majorNum!=0){
                classDao.insert(classes);
                return 0;//成功插入
            }
            else{
                return 1;//专业不存在，无法插入
            }
        }
        else {
            return 2;//当前班级已存在，无法插入
        }
    }
    /**
     * 删除
     * @param classCode 班级代码
     * @return int 0成功,1失败
     */
    @Override
    public int delete(String classCode) {
        int num=classDao.getByCode(classCode).size();
        int studentNum=studentDao.getByAttribute("classCode",classCode).size();
        if(num!=0){
            if(studentNum==0){
                classDao.delete(classCode);
                return 0;
            }
            else {
                return 1;//该班级下学生非空，无法删除
            }
        }
        else {
            return 2;//学生不存在，无法删除
        }
    }
    /**
     * 更新
     * @param classes 班级实体
     * @return int 0成功,1失败
     */
    @Override
    public int update(Classes classes) {
        int num=classDao.getByCode(classes.getClassCode()).size();
        int majorNum=majorDao.getByCode(classes.getMajorCode()).size();
        if(num!=0){
            if(majorNum!=0){
                classDao.update(classes);
                return 0;//成功更新
            }
            else{
                return 1;//专业不存在，无法更新
            }
        }
        else {
            return 2;//当前班级不存在，无法更新
        }
    }

    /**
     * 多条件查询
     * @param classes 班级实体
     * @return result list
     */
    @Override
    public Map<Object, Object> query(Classes classes, String majorName, String facultyName, int pageIndex, int pageSize){
        return classDao.query(classes,majorName,facultyName,pageIndex,pageSize);
    }
}
