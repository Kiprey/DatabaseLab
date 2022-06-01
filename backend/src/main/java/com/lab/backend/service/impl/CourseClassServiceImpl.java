package com.lab.backend.service.impl;

import com.lab.backend.repository.*;
import com.lab.backend.service.CourseClassService;
import com.lab.backend.domain.CourseClass;
import com.lab.backend.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CourseClassServiceImpl implements CourseClassService {
    @Resource
    private CourseClassDao courseClassDao;
    @Resource
    private CourseDao courseDao;
    @Resource
    private TeacherDao teacherDao;
    @Resource
    private StudentCourseDao studentCourseDao;

    /**
     * 插入
     *
     * @param courseClass 实体
     * @return 0:成功, 1:失败，课程不存在, 2:失败，教师不存在，3:失败,该课程班级已存在
     */
    @Override
    public int insert(CourseClass courseClass) {
        if (courseDao.getByCode(courseClass.getCourseID()).size() == 0) {
            return 1;
        } else if (teacherDao.getByAttribute("teacherID",courseClass.getTeacherID()).size() == 0) {
            return 2;
        } else if (courseClassDao.getByAttribute("courseClassID", courseClass.getCourseClassID()).size() > 0) {
            return 3;
        } else {
            courseClassDao.insert(courseClass);
            return 0;
        }
    }

    /**
     * 按ID删除
     *
     * @param courseClassID 教师编号
     * @return 0:成功, 1:失败，对应学生选课非空, 2:失败，该课程班级不存在
     */
    @Override
    public int delete(String courseClassID) {
        if (studentCourseDao.getByAttribute("courseClassID",courseClassID).size() != 0) {
            return 1;
        } else if (courseClassDao.getByAttribute("courseClassID", courseClassID).size() == 0) {
            return 2;
        } else {
            courseClassDao.delete(courseClassID);
            return 0;
        }
    }


    /**
     * 更新
     *
     * @param courseClass 实体
     * @return 0:成功, 1:失败，课程不存在, 2:失败，教师不存在，3:失败,该课程班级不存在
     */
    @Override
    public int update(CourseClass courseClass) {
        if (courseDao.getByCode(courseClass.getCourseID()).size() == 0) {
            return 1;
        } else if (teacherDao.getByAttribute("teacherID",courseClass.getTeacherID()).size() == 0) {
            return 2;
        } else if (courseClassDao.getByAttribute("courseClassID", courseClass.getCourseClassID()).size() == 0) {
            return 3;
        } else {
            courseClassDao.update(courseClass);
            return 0;
        }
    }


    /**
     * 多条件查询（管理员）
     *
     * @param map：构成查询条件
     */
    @Override
    public Map<Object, Object> query(Map<String,Object> map, int pageIndex, int pageSize) {
        return courseClassDao.query(map, pageIndex, pageSize);
    }


    /**
     * 多条件查询（学生和教师）
     *
     * @param map：构成查询条件
     */
    @Override
    public Map<Object, Object> queryByUser(Map<String,Object> map, int pageIndex, int pageSize) {
        return courseClassDao.queryByUser(map, pageIndex, pageSize);
    }

    /**
     * 全部查询
     *
     * @return result list
     */
    @Override
    public List<CourseClass> getList() {
        return courseClassDao.getList();
    }

    /**
     * 插入（教师使用）
     *
     * @param courseClass 实体
     * @return 0:成功, 1:失败，课程不存在, 2:失败,该课程班级已存在
     */
    @Override
    public int insertByTeacher(CourseClass courseClass){
        courseClass.setTeacherID(SecurityUtil.getUserName());
        if (courseDao.getByCode(courseClass.getCourseID()).size() == 0) {
            return 1;
        } else if (courseClassDao.getByAttribute("courseClassID", courseClass.getCourseClassID()).size() > 0) {
            return 2;
        } else {
            courseClassDao.insert(courseClass);
            return 0;
        }
    }
    /**
     * 更新（教师使用）
     *
     * @param courseClass 实体
     * @return 0:成功, 1:失败，课程不存在, 2:失败,该课程班级不存在
     */
    @Override
    public int updateByTeacher(CourseClass courseClass) {
        courseClass.setTeacherID(SecurityUtil.getUserName());
        if (courseDao.getByCode(courseClass.getCourseID()).size() == 0) {
            return 1;
        } else if (courseClassDao.getByAttribute("courseClassID", courseClass.getCourseClassID()).size() == 0) {
            return 2;
        } else {
            courseClassDao.update(courseClass);
            return 0;
        }
    }
    /**
     * 按ID删除（教师用）
     *
     * @param courseClassID 课程班级编号
     * @return 0:成功, 1:失败，对应学生选课非空, 2:失败，该课程班级不存在, 3:失败，该课程不属于该教师
     */
    @Override
    public int deleteByTeacher(String courseClassID) {
        if (studentCourseDao.getByAttribute("courseClassID",courseClassID).size() != 0) {
            return 1;
        } else if (courseClassDao.getByAttribute("courseClassID", courseClassID).size() == 0) {
            return 2;
        } else {
            CourseClass courseClass= courseClassDao.getByAttribute("courseClassID", courseClassID).get(0);
            if(!Objects.equals(courseClass.getTeacherID(), SecurityUtil.getUserName()))
                return 3;
            courseClassDao.delete(courseClassID);
            return 0;
        }
    }
}