package com.lab.backend.service.impl;


import com.lab.backend.domain.CourseClass;
import com.lab.backend.domain.Teacher;
import com.lab.backend.repository.CourseClassDao;
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
    @Resource
    private CourseClassDao courseClassDao;

    /**
     * 插入
     *
     * @param teacher 教师实体
     * @return 0:成功, 1:失败，院系不存在, 2:失败，该教师已存在
     */
    @Override
    public int insert(Teacher teacher) {
        Teacher temp = new Teacher();
        temp.setTeacherID(teacher.getTeacherID());
        int num = teacherDao.query(temp).size();
        if ((facultyDao.getByCode(teacher.getFacultyCode()).size() == 0)) {
            return 1;
        } else if (num == 0) {
            teacherDao.insert(teacher);
            return 0;
        } else {
            return 2;
        }
    }

    /**
     * 按ID删除
     *
     * @param teacherID 教师编号
     * @return 0:成功, 1:失败，对应课程班级非空, 2:失败，该教师不存在
     */
    @Override
    public int delete(String teacherID) {
        Teacher temp = new Teacher();
        temp.setTeacherID(teacherID);
        CourseClass courseClass = new CourseClass();
        courseClass.setTeacherID(teacherID);
        int num = teacherDao.query(temp).size();
        if (courseClassDao.query(courseClass).size() != 0) {
            return 1;
        } else if (num != 0) {
            teacherDao.delete(teacherID);
            return 0;
        } else {
            return 2;
        }
    }

    /**
     * 更新
     *
     * @param teacher 教师实体
     * @return 0:成功, 1:失败，院系不存在, 2:失败，该教师不存在
     */
    @Override
    public int update(Teacher teacher) {
        Teacher temp = new Teacher();
        temp.setTeacherID(teacher.getTeacherID());
        int num = teacherDao.query(temp).size();
        if ((facultyDao.getByCode(teacher.getFacultyCode()).size() == 0)) {
            return 1;
        } else if (num != 0) {
            teacherDao.update(teacher);
            return 0;
        } else {
            return 2;
        }
    }

    /**
     * 多条件查询
     *
     * @param teacher 教师实体：查询条件
     * @return result list
     */
    @Override
    public List<Teacher> query(Teacher teacher) {
        return teacherDao.query(teacher);
    }

    /**
     * 全部查询
     *
     * @return result list
     */
    @Override
    public List<Teacher> getList() {
        return teacherDao.getList();
    }
}
