package com.lab.backend.service.impl;


import com.lab.backend.domain.Teacher;
import com.lab.backend.repository.CourseClassDao;
import com.lab.backend.repository.FacultyDao;
import com.lab.backend.repository.TeacherDao;
import com.lab.backend.service.TeacherService;
import com.lab.backend.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        int num = teacherDao.getByAttribute("teacherID",teacher.getTeacherID()).size();
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
        int num = teacherDao.getByAttribute("teacherID",teacherID).size();
        if (courseClassDao.getByAttribute("teacherID",teacherID).size() != 0) {
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
        int num = teacherDao.getByAttribute("teacherID",teacher.getTeacherID()).size();
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
     * @param map：查询条件
     * @return result list
     */
    @Override
    public Map<Object, Object> query(Map<String,Object> map, int pageIndex, int pageSize) {
            return teacherDao.query(map,pageIndex,pageSize);
    }

    /**
     * 多条件查询，得不到ID
     *
     * @param map：查询条件
     * @return result list
     */
    @Override
    public Map<Object, Object> queryWithoutID(Map<String,Object> map, int pageIndex, int pageSize) {
        return teacherDao.queryWithoutID(map,pageIndex,pageSize);
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

    /**
     * 得到自己的信息
     *
     * @return result list
     */
    @Override
    public List<Teacher> info() {
        Map<String,Object> map = new HashMap<>();
        map.put("teacherName",null);
        map.put("teacherID",SecurityUtil.getUserName());
        map.put("facultyCode",null);
        map.put("facultyName",null);
        return (List<Teacher>) teacherDao.query(map, 1,1).get("tableData");
    }
}
