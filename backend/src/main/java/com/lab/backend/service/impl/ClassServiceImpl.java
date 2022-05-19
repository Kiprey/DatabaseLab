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
     * 全部查询
     * @return result list
     */
    @Override
    public List<Classes> getList() {
        return classDao.getList();
    }
    /**
     * 多条件查询
     * @param classes 班级实体
     * @return result list
     */
    @Override
    public Map<Object, Object> query(Classes classes, String majorName, int pageIndex, int pageSize){
        //采用名称的条件是名字非空且专业代码为空
        // 其余情况都优先按照专业代码的设置查询
        if(majorName!=null&&!majorName.trim().isEmpty()&&(classes.getClassCode()==null||classes.getClassCode().trim().isEmpty())){
            String majorCode = null;
            //根据名字获取code
            List<Major> list=majorDao.getByAttribute("majorName", majorName);
            if(!list.isEmpty()) {
                majorCode = list.get(0).getMajorCode();
            }
            //查询的code为空时直接返回结果为0
            if(majorCode==null){
                Map<Object, Object> response=new HashMap<>();
                response.put("total",0);
                return response;
            }
            //否则将code加入多条件查询
            else {
                classes.setMajorCode(majorCode);
                return classDao.query(classes,pageIndex,pageSize);
            }
        }
        else {
            return classDao.query(classes,pageIndex,pageSize);
        }
    }
}
