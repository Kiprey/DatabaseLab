package com.lab.backend.service.impl;

import com.lab.backend.domain.Faculty;
import com.lab.backend.repository.FacultyDao;
import com.lab.backend.service.FacultyService;
import com.lab.backend.service.MajorService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.List;
@Service
public class FacultyServiceImpl  implements FacultyService {
    @Resource
    private FacultyDao facultyDao;
    @Resource
    private MajorService majorService;
    /**
     * 插入
     * @param faculty 院系实体
     * @return 执行结果
     */
    @Override
    public boolean insert(Faculty faculty) {
        int num=facultyDao.getByCode(faculty.getFacultyCode()).size();
        if(num==0){
            facultyDao.insert(faculty);
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * 删除
     * @param facultyCode 院系代码
     * @return int 0成功,1失败
     */
    @Override
    public int delete(String facultyCode) {
        int num=facultyDao.getByCode(facultyCode).size();
        int majorNum=majorService.getListByFacultyCode(facultyCode).size();
        if(num!=0){
            if(majorNum==0){
                facultyDao.delete(facultyCode);
                return 0;
            }
            else{
                return 1;//该院系下专业非空，无法删除
            }
        }
        else {
            return 2;//院系不存在
        }
    }
    /**
     * 更新
     * @param faculty 院系实体
     * @return int 0成功,1失败
     */
    @Override
    public boolean update(Faculty faculty) {
        int num=facultyDao.getByCode(faculty.getFacultyCode()).size();
        if(num!=0){
            facultyDao.update(faculty);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 全部查询
     * @return result list
     */
    @Override
    public List<Faculty> getList() {
        return facultyDao.getList();
    }

    /**
     * 按name查询
     * @param name 院系名字
     * @return result list
     */
    @Override
    public List<Faculty> getListByName(String name){
        return facultyDao.getByAttribute("facultyName",name);
    }
}
