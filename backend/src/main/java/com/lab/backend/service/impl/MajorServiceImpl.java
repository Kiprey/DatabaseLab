package com.lab.backend.service.impl;


import com.lab.backend.domain.Faculty;
import com.lab.backend.domain.Major;
import com.lab.backend.repository.ClassDao;
import com.lab.backend.repository.FacultyDao;
import com.lab.backend.repository.MajorDao;
import com.lab.backend.service.MajorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MajorServiceImpl implements MajorService {
    @Resource
    private MajorDao majorDao;
    @Resource
    private FacultyDao facultyDao;
    @Resource
    private ClassDao classDao;
    /**
     * 插入
     * @param major 专业实体
     * @return 执行结果
     */
    @Override
    public int insert(Major major) {
        int num=majorDao.getByCode(major.getMajorCode()).size();
        int facultyNum=facultyDao.getByCode(major.getFacultyCode()).size();
        if(num==0){
            if(facultyNum!=0){
                majorDao.insert(major);
                return 0;//成功插入
            }
            else{
                return 1;//院系不存在，无法插入
            }
        }
        else {
            return 2;//当前专业已存在，无法插入
        }
    }
    /**
     * 删除
     * @param majorCode 专业代码
     * @return int 0成功,1失败
     */
    @Override
    public int delete(String majorCode) {
        int num=majorDao.getByCode(majorCode).size();
        int classNum=classDao.getByAttribute("majorCode",majorCode).size();
        if(num!=0){
            if(classNum==0){
                majorDao.delete(majorCode);
                return 0;
            }
            else {
                return 1;//该专业下班级非空，无法删除
            }
        }
        else {
            return 2;//专业不存在，无法删除
        }
    }


    /**
     * 更新
     * @param major 专业实体
     * @return int 0成功,1失败
     */
    @Override
    public int update(Major major) {
        int num=majorDao.getByCode(major.getMajorCode()).size();
        int facultyNum=facultyDao.getByCode(major.getFacultyCode()).size();
        if(num!=0){
            if(facultyNum!=0){
                majorDao.update(major);
                return 0;//成功更新
            }
            else{
                return 1;//院系不存在，无法更新
            }
        }
        else {
            return 2;//当前专业不存在，无法更新
        }
    }


    /**
     * 全部查询
     * @return result list
     */
    @Override
    public List<Major> getList() {
        return majorDao.getList();
    }


    /**
     * 按院系名称查询
     * @param facultyName 专业名字
     * @return result list
     */
    @Override
    public List<Major> getListByFacultyName(String facultyName){
        String facultyCode;
        List<Faculty> list=facultyDao.getByAttribute("facultyName", facultyName);
        if(!list.isEmpty()) {
            facultyCode = list.get(0).getFacultyCode();
            return majorDao.getByAttribute("facultyCode", facultyCode);
        }
        else {
            return new ArrayList<>();
        }
    }

    /**
     * 多条件查询
     * @param major 专业实体
     * @return result list
     */
    @Override
    public Map<Object, Object> query(Major major, int pageIndex, int pageSize){
        return majorDao.query(major,pageIndex,pageSize);
    }
}
