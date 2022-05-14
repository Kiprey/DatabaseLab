package com.lab.backend.service.impl;


import com.lab.backend.domain.Teacher;
import com.lab.backend.repository.FacultyDao;
import com.lab.backend.repository.TeacherDao;
import com.lab.backend.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Resource
    private TeacherDao teacherDao;
    @Resource
    private FacultyDao facultyDao;


    /**
     * 插入
     * @param teacher 教师实体
     * @return 0:成功, 1:失败，院系不存在, 2:失败，该教师已存在
     */
    @Override
    public int insert(Teacher teacher) {
        int num = teacherDao.getByID(teacher.getTeacherID()).size();
        if ((facultyDao.getByCode(teacher.getFacultyCode()).size() == 0)) {
            return 1;
        } else if (num==0) {
            teacherDao.insert(teacher);
            return 0;
        } else {
            return 2;
        }
    }

    /**
     * 按ID删除
     * @param teacherID 教师编号
     * @return 0:成功, 1:失败，对应课程班级非空, 2:失败，该教师不存在
     */
    @Override
    public int delete(String teacherID) {
        return 0;
    }

//    /**
//     * 按院系删除
//     *
//     * @param facultyCode 院系代码
//     * @return 0:成功, 1:失败，对应课程班级非空, 2:失败，该院系不存在
//     */
//    public int deleteByFaculty(String facultyCode) {
//        return 0;
//    }

    /**
     * 更新
     * @param teacher 教师实体
     * @return 0:成功, 1:失败，院系不存在, 2:失败，该教师不存在
     */
    @Override
    public int update(Teacher teacher) {
        int num = teacherDao.getByID(teacher.getTeacherID()).size();
        if ((facultyDao.getByCode(teacher.getFacultyCode()).size() == 0)) {
            return 1;
        } else if (num!=0) {
            teacherDao.update(teacher);
            return 0;
        } else {
            return 2;
        }
    }

    /**
     * 按name查询
     * @param name 教师名字
     * @return result list
     */
    @Override
    public List<Teacher> getByName(String name) {
        return teacherDao.getByName(name);
    }

    /**
     * 按ID查询
     * @param ID 教师编号
     * @return result list
     */
    @Override
    public List<Teacher> getByID(String ID) {
        return teacherDao.getByID(ID);
    }

    /**
     * 按facultyCode查询
     * @param facultyCode 院系代码
     * @return result list
     */
    @Override
    public List<Teacher> getByFaculty(String facultyCode) {
        return teacherDao.getByFaculty(facultyCode);
    }

    /**
     * 全部查询
     * @return result list
     */
    @Override
    public List<Teacher> getList() {
        return teacherDao.getList();
    }
}
