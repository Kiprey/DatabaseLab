package com.lab.backend.service.impl;

import com.lab.backend.domain.StudentCourse;
import com.lab.backend.repository.CourseDao;
import com.lab.backend.repository.StudentCourseDao;
import com.lab.backend.repository.StudentDao;
import com.lab.backend.service.StudentCourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentCourseServiceImpl implements StudentCourseService {
    @Resource
    private StudentCourseDao studentcourseDao;
    @Resource
    private StudentDao studentDao;
    @Resource
    private CourseDao courseDao;
    @Override
    public int insert(StudentCourse studentcourse){
        int num = studentcourseDao.getByCode(studentcourse.getCourseClassID()).size();
        if(studentDao.getByID(studentcourse.getStudentID()).size() == 0 || num==0){
            return 1;
        }
        else{
            studentcourseDao.insert(studentcourse);
            return 0;
        }
    }
    @Override
    public int delete(String courseID){
        if(studentcourseDao.getByCode(courseID).size() == 0){
            return 0;
        }
        else {
            studentcourseDao.delete(courseID);
            return 1;
        }
    }
    @Override
    public int update(StudentCourse studentcourse){
        int num = studentcourseDao.getByCode(studentcourse.getCourseClassID()).size();
        if(studentDao.getByID(studentcourse.getStudentID()).size() == 0 || num == 0){
            return 0;
        }
        else{
            studentcourseDao.insert(studentcourse);
            return 1;
        }
    }
    @Override
    public List<StudentCourse> getByAttribute(String attribute, String name){
        return studentcourseDao.getByAttribute(attribute, name);
    }
    @Override
    public List<StudentCourse> getByCode(String code){
        return studentcourseDao.getByCode(code);
    }
    @Override
    public List<StudentCourse> getList(){
        return studentcourseDao.getList();
    }

}
