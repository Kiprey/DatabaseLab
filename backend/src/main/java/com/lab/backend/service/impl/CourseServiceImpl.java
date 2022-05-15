package com.lab.backend.service.impl;

import com.lab.backend.domain.Course;
import com.lab.backend.repository.CourseDao;
import com.lab.backend.repository.FacultyDao;
import com.lab.backend.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Resource
    private CourseDao courseDao;
    @Resource
    private FacultyDao facultyDao;

    /**
     * 插入课程
     * @param course 课程实体
     * @return 0:成功, 1:失败，院系不存在, 2:失败，该课程已存在
     */
    @Override
    public int insert(Course course) {
        int num = courseDao.getByCode(course.getCourseID()).size();
        if ((facultyDao.getByCode(course.getFacultyCode()).size() == 0)) {
            return 1;
        } else if (num==0) {
            courseDao.insert(course);
            return 0;
        } else {
            return 2;
        }
    }

    /**
     * 根据课程id删除课程
     * @param courseID 课程id值
     * @return 返回值为1，删除成功；返回值为0，没有找到要删除的课程，删除失败
     */
    @Override
    public int delete(String courseID){
        int num=courseDao.getByCode(courseID).size();
        if(num!=0){
            courseDao.delete(courseID);
            return 1;
        }
        else {
            return 0;
        }
    }

    /**
     * 更新
     * @param course 课程实体
     * @return 0:成功, 1:失败，院系不存在, 2:失败，该课程已存在
     */
    public int update(Course course) {
        int num = courseDao.getByCode(course.getCourseID()).size();
        if ((facultyDao.getByCode(course.getFacultyCode()).size() == 0)) {
            return 1;
        } else if (num==0) {
            courseDao.update(course);
            return 0;
        } else {
            return 2;
        }
    }
    public List<Course> getByAttribute(String attribute, String name){
        return courseDao.getByAttribute(attribute,name);
    }
    public List<Course> getByCode(String code){
        return courseDao.getByCode(code);
    }
    public List<Course> getList(){
        return courseDao.getList();
    }
}
