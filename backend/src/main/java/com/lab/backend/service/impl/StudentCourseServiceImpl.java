package com.lab.backend.service.impl;

import com.lab.backend.domain.CourseClass;
import com.lab.backend.domain.StudentCourse;
import com.lab.backend.repository.CourseClassDao;
import com.lab.backend.repository.CourseDao;
import com.lab.backend.repository.StudentCourseDao;
import com.lab.backend.repository.StudentDao;
import com.lab.backend.service.StudentCourseService;
import com.lab.backend.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class StudentCourseServiceImpl implements StudentCourseService {
    @Resource
    private StudentCourseDao studentcourseDao;
    @Resource
    private StudentDao studentDao;
    @Resource
    private CourseClassDao courseClassDao;
//    @Resource
//    private CourseDao courseDao;

    /**
     * 插入
     * @param studentcourse 选课实体
     * @return 结果码 0：插入成功；1：没有该学生；2：没有课程班级；3：已经有了该选课
     */
    @Override
    public int insert(StudentCourse studentcourse){
        int studentcourse_num = studentcourseDao.getByCode(studentcourse.getCourseClassID(),studentcourse.getStudentID()).size();
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
     * @param studentID 学生ID
     * @return 结果码 0：成功删除；1：没有找到要删除的选课；
     */
    @Override
    public int delete(String courseClassID,String studentID){
        int course_num = studentcourseDao.getByCode(courseClassID,studentID).size();
        if(course_num != 0){
            studentcourseDao.delete(courseClassID,studentID);
            return 0;
        } else {
            return 2;
        }
    }

    /**
     * 更新
     * @param studentcourse 选课实体
     * @return 结果码 0：插入成功；1：没有找到要删除的选课；
     */
    @Override
    public int update(StudentCourse studentcourse){
        int studentcourse_num = studentcourseDao.getByCode(studentcourse.getCourseClassID(),studentcourse.getStudentID()).size();
        if(studentcourse_num != 0 ){
            studentcourseDao.update(studentcourse);
            return 0;
        } else {
            return 1;
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
     * @param courseClassID 班级课程ID
     * @param studentID 学生ID
     * @return 查询结果
     */
    @Override
    public List<StudentCourse> getByCode(String courseClassID, String studentID){
        return studentcourseDao.getByCode(courseClassID,studentID);
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
     * @param map 查询条件
     * @param pageIndex 起始页
     * @param pageSize 大小
     * @return 查询结果
     */
    @Override
    public Map<Object, Object> query(Map<String,Object> map, int pageIndex, int pageSize) {
        return studentcourseDao.query(map, pageIndex, pageSize);
    }

    /**
     * 计算平均分
     * @param studentID 学生ID
     * @return 计算结果
     */
    @Override
    public List<Integer> getMeanScore(String studentID){
        List<StudentCourse> score_list = studentcourseDao.getByAttribute("studentID",studentID);
        if(score_list.size() == 0) return null;
        List<Integer> result = new ArrayList<>();
        Integer count = score_list.size();
        Integer algorithm_mean = 0;
//        Integer weigh_mean = 0;
        for (StudentCourse tem : score_list) {
            algorithm_mean += tem.getScore();
//            weigh_mean += tem.getScore()
        }
        if(algorithm_mean != 0) result.add(algorithm_mean/count);
        return result;
    }
    /**
     * 插入（学生使用）
     * @param courseClassID 选课ID
     * @return 结果码 0：插入成功；1：没有该学生；2：没有课程班级；3：已经有了该选课
     */
    @Override
    public int insertByStudent(String courseClassID){
        int studentcourse_num = studentcourseDao.getByCode(courseClassID,SecurityUtil.getUserName()).size();
        int courseclass_num = courseClassDao.getByCode(courseClassID).size();
        int student_num = studentDao.getByID(SecurityUtil.getUserName()).size();
        if(student_num != 0 && courseclass_num !=0 && studentcourse_num == 0){
            StudentCourse studentcourse = new StudentCourse();
            studentcourse.setStudentID(SecurityUtil.getUserName());
            studentcourse.setCourseClassID(courseClassID);
            studentcourse.setScore(0);
            studentcourseDao.insert(studentcourse);
            return 0;
        } else if (student_num == 0) {
            return 1;
        } else if (courseclass_num == 0) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * 删除（学生使用）
     * @param courseClassID 课程班级ID
     * @return 结果码 0：成功删除；1：没有找到要删除的班级课程；2：没有权限
     */
    @Override
    public int deleteByStudent(String courseClassID){
        String studentID=SecurityUtil.getUserName();
        int course_num = studentcourseDao.getByCode(courseClassID,studentID).size();
        if(course_num != 0){
            String teacherID = courseClassDao.getByCode(courseClassID).get(0).getTeacherID();
            if(!Objects.equals(teacherID, SecurityUtil.getUserName())){
                studentcourseDao.delete(courseClassID,studentID);
                return 0;
            } else{
                return 2;
            }
        } else {
            return 1;
        }
    }

    /**
     * 模糊查询（学生使用）
     * @param pageIndex 起始页
     * @param pageSize 大小
     * @return 查询结果
     */
    @Override
    public Map<Object, Object> queryByStudent(Map<String,Object> map, int pageIndex, int pageSize) {
        map.put("studentID",SecurityUtil.getUserName());
        return studentcourseDao.query(map, pageIndex, pageSize);
    }
    /**
     * 模糊查询（老师使用）
     * @param pageIndex 起始页
     * @param pageSize 大小
     * @return 查询结果
     */
    @Override
    public Map<Object, Object> queryByTeacher(Map<String,Object> map, int pageIndex, int pageSize) {
        map.put("teacherID",SecurityUtil.getUserName());
        return studentcourseDao.queryByTeacher(map, pageIndex, pageSize);
    }
    /**
     * 更新（老师使用）
     * @param studentcourse 选课实体
     * @return 结果码 0：插入成功；1：没有找到要删除的选课；2:没权限
     */
    @Override
    public int updateByTeacher(StudentCourse studentcourse){
        int flag=0;
        List<CourseClass> list=courseClassDao.getByAttribute("teacherID",SecurityUtil.getUserName());
        for(CourseClass item:list){
            if (Objects.equals(item.getCourseClassID(), studentcourse.getCourseClassID())) {
                flag = 1;
                break;
            }
        }
        int studentcourse_num = studentcourseDao.getByCode(studentcourse.getCourseClassID(),studentcourse.getStudentID()).size();
        if(studentcourse_num != 0 && flag==1){
            studentcourseDao.update(studentcourse);
            return 0;
        } else if (studentcourse_num == 0 ) {
            return 1;
        } else {
            return 2;
        }
    }
}
