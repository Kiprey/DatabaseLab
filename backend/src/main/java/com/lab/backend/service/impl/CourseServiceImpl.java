package com.lab.backend.service.impl;

import com.lab.backend.domain.Course;
import com.lab.backend.repository.CourseDao;
import com.lab.backend.repository.FacultyDao;
import com.lab.backend.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {
    @Resource
    private CourseDao courseDao;
    @Resource
    private FacultyDao facultyDao;

    /**
     * 插入课程
     * @param course 课程实体
     * @return 结果码 0:成功插入；1:院系不存在；2:该课程已存在
     */
    @Override
    public int insert(Course course) {
        int course_num = courseDao.getByCode(course.getCourseID()).size();
        int faculty_num = facultyDao.getByCode(course.getFacultyCode()).size();
        if (faculty_num != 0 && course_num == 0) {
            courseDao.insert(course);
            return 0;
        } else if (faculty_num == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * 根据课程id删除课程
     * @param courseID 课程id值
     * @return 结果码 0：删除成功；1：没有所要删除的课程
     */
    @Override
    public int delete(String courseID){
        int course_num = courseDao.getByCode(courseID).size();
        if(course_num != 0){
            courseDao.delete(courseID);
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 更新
     * @param course 课程实体
     * @return 结果码 0:成功更新；1:院系不存在；2:该课程不存在
     */
    public int update(Course course) {
        int course_num = courseDao.getByCode(course.getCourseID()).size();
        int faculty_num = facultyDao.getByCode(course.getFacultyCode()).size();
        if (faculty_num != 0 && course_num != 0) {
            courseDao.update(course);
            return 0;
        } else if (faculty_num == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * 查询指定字段指定值的数据
     * @param attribute 字段名
     * @param name 字段值
     * @return 查询结果
     */
    public List<Course> getByAttribute(String attribute, String name){
        return courseDao.getByAttribute(attribute,name);
    }

    /**
     * 返回指定课程id的查询结果
     * @param code 课程id值
     * @return 查询结果
     */
    public List<Course> getByCode(String code){
        return courseDao.getByCode(code);
    }

    /**
     * 查询course表所有数据
     * @return 查询结果
     */
    public List<Course> getList(){
        return courseDao.getList();
    }

    /**
     * 模糊查询course表
     * @param course 课程实体
     * @param pageIndex 起始页
     * @param pageSize 大小
     * @return 查询结果
     */
    @Override
    public Map<Object, Object> query(Course course, int pageIndex, int pageSize) {
        return courseDao.query(course, pageIndex, pageSize);
    }
}
