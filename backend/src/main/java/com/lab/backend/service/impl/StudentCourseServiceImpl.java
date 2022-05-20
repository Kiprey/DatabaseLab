package com.lab.backend.service.impl;

import com.lab.backend.domain.StudentCourse;
import com.lab.backend.repository.CourseClassDao;
import com.lab.backend.repository.StudentCourseDao;
import com.lab.backend.repository.StudentDao;
import com.lab.backend.service.StudentCourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class StudentCourseServiceImpl implements StudentCourseService {
    @Resource
    private StudentCourseDao studentcourseDao;
    @Resource
    private StudentDao studentDao;
    @Resource
    private CourseClassDao courseClassDao;

    /**
     * 插入
     * @param studentcourse 选课实体
     * @return 结果码 0：插入成功；1：没有该学生；2：没有课程班级；3：已经有了该选课
     */
    @Override
    public int insert(StudentCourse studentcourse){
        int studentcourse_num = studentcourseDao.getByCode(studentcourse.getCourseClassID()).size();
        int courseclass_num = courseClassDao.getByCode(studentcourse.getCourseClassID()).size();
        int student_num = studentDao.getByID(studentcourse.getStudentID()).size();
        if(student_num != 0 && courseclass_num !=0 && studentcourse_num == 0){
            studentcourseDao.insert(studentcourse);
            return 0;
        } else if(student_num == 0){
            return 1;
        } else if (courseclass_num == 0) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * 删除
     * @param courseClassID 课程班级ID
     * @return 结果码 0：成功删除；1：没有找到要删除的班级课程
     */
    @Override
    public int delete(String courseClassID){
        int studentcourse_num = studentcourseDao.getByCode(courseClassID).size();
        if(studentcourse_num != 0){
            studentcourseDao.delete(courseClassID);
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 更新
     * @param studentcourse 选课实体
     * @return 结果码 0：插入成功；1：没有该学生；2：没有课程班级；3：没有该选课
     */
    @Override
    public int update(StudentCourse studentcourse){
        int studentcourse_num = studentcourseDao.getByCode(studentcourse.getCourseClassID()).size();
        int courseclass_num = courseClassDao.getByCode(studentcourse.getCourseClassID()).size();
        int student_num = studentDao.getByID(studentcourse.getStudentID()).size();
        if(student_num != 0 && courseclass_num !=0 && studentcourse_num != 0){
            studentcourseDao.update(studentcourse);
            return 0;
        } else if(student_num == 0){
            return 1;
        } else if (courseclass_num == 0) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * 查询指定字段指定值的数据
     * @param attribute 字段名
     * @param name 字段值
     * @return 查询结果
     */
    @Override
    public List<StudentCourse> getByAttribute(String attribute, String name){
        return studentcourseDao.getByAttribute(attribute, name);
    }

    /**
     * 查询指定课程班级ID的数据
     * @param code 选课ID
     * @return 查询结果
     */
    @Override
    public List<StudentCourse> getByCode(String code){
        return studentcourseDao.getByCode(code);
    }

    /**
     * 查询StudentClass表所有数据
     * @return 返回查询结果
     */
    @Override
    public List<StudentCourse> getList(){
        return studentcourseDao.getList();
    }

    /**
     * 模糊查询
     * @param studentCourse 选课实体
     * @param pageIndex 起始页
     * @param pageSize 大小
     * @return 查询结果
     */
    @Override
    public Map<Object, Object> query(StudentCourse studentCourse, int pageIndex, int pageSize) {
        return studentcourseDao.query(studentCourse, pageIndex, pageSize);
    }

}
