package com.lab.backend.service.impl;

import com.lab.backend.domain.Classes;
import com.lab.backend.domain.Major;
import com.lab.backend.repository.ClassDao;
import com.lab.backend.repository.MajorDao;
import com.lab.backend.service.ClassService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class ClassServiceImpl implements ClassService {
    @Resource
    private ClassDao classDao;
    @Resource
    private MajorDao majorDao;
    /**
     * 插入
     * @param classes 班级实体
     * @return 执行结果
     */
    @Override
    public int insert(Classes classes) {
        int num=classDao.getByName(classes.getClassName()).size();
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
     * @param className 班级代码
     * @return int 0成功,1失败
     */
    @Override
    public int delete(String className) {
        int num=classDao.getByName(className).size();
        if(num!=0){
            classDao.delete(className);
            return 0;
        }
        else {
            return 1;
        }
    }
    /**
     * 更新
     * @param classes 班级实体
     * @return int 0成功,1失败
     */
    @Override
    public int update(Classes classes) {
        int num=classDao.getByName(classes.getClassName()).size();
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
     * 全部查询
     * @return result list
     */
    @Override
    public List<Classes> getList() {
        return classDao.getList();
    }

    /**
     * 按name查询
     * @param name 班级名字
     * @return result list
     */
    @Override
    public List<Classes> getListByName(String name){
        return classDao.getByName(name);
    }
    /**
     * 按专业名称查询
     * @param majorName 专业名字
     * @return result list
     */
    @Override
    public List<Classes> getListByMajorName(String majorName){
        String majorCode;
        List<Major> list=majorDao.getByAttribute("majorName", majorName);
        if(!list.isEmpty()) {
            majorCode = list.get(0).getMajorCode();
            return classDao.getByAttribute("majorCode", majorCode);
        }
        else {
            return new ArrayList<>();
        }
    }

    /**
     * 按专业code查询
     * @param majorCode 专业代码
     * @return result list
     */
    @Override
    public List<Classes> getListByMajorCode(String majorCode){
        return classDao.getByAttribute("majorCode", majorCode);

    }
}
