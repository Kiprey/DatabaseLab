package com.lab.backend.service.impl;

import com.lab.backend.repository.CourseClassDao;
import com.lab.backend.service.CourseClassService;
import com.lab.backend.domain.CourseClass;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseClassServiceImpl implements CourseClassService {
    @Resource
    private CourseClassDao courseClassDao;


    /**
     * 插入
     *
     * @param courseClass 实体
     * @return 0:成功, 1:失败，课程不存在, 2:失败，教师不存在，3:失败,该课程班级已存在
     */
    @Override
    public int insert(CourseClass courseClass) {
        return 0;
    }

    /**
     * 按ID删除
     *
     * @param courseClassID 教师编号
     * @return 0:成功, 1:失败，对应学生选课非空, 2:失败，该课程班级不存在
     */
    @Override
    public int delete(String courseClassID) {
        return 0;
    }


    /**
     * 更新
     *
     * @param courseClass 实体
     * @return 0:成功, 1:失败，课程不存在, 2:失败，教师不存在，3:失败,该课程班级不存在
     */
    @Override
    public int update(CourseClass courseClass) {
        int num = courseClassDao.query(courseClass).size();
        return 0;
    }


    /**
     * 多条件查询
     *
     * @param courseClass 课程班级实体：构成查询条件
     */
    @Override
    public List<CourseClass> query(CourseClass courseClass) {
        return courseClassDao.query(courseClass);
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
}