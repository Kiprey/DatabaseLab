package com.lab.backend.repository;

import com.lab.backend.domain.Course;
import com.lab.backend.domain.Faculty;
import com.lab.backend.domain.StudentCourse;
import com.lab.backend.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class StudentCourseDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 插入
     * @param studentcourse 一个StudentCourse实体
     */
    public void insert(StudentCourse studentcourse) {
        String sql="insert into studentcourse values (?,?,?)";
        jdbcTemplate.update(sql,
                studentcourse.getCourseClassID(),
                studentcourse.getStudentID(),
                studentcourse.getScore());
    }

    /**
     * 按照主键删除
     * @param courseClassID courseClassID字段的值
     */
    public void delete(String courseClassID,String studentID) {
        String sql="delete from studentcourse where courseClassID = ? and studentID = ?";
        jdbcTemplate.update(sql, courseClassID,studentID);
    }

    /**
     * 按照主键更新
     * @param studentcourse 一个studentcourse实体
     */
    public void update(StudentCourse studentcourse) {
        String sql="UPDATE studentcourse SET score = ? where courseClassID = ? and studentID = ?";
        jdbcTemplate.update(sql,
                studentcourse.getScore(),
                studentcourse.getCourseClassID(),
                studentcourse.getStudentID()
                );
    }

    /**
     * 按照字段值查询
     * @param attribute 字段名
     * @param name 字段值
     * @return 返回查询结果
     */
    public List<StudentCourse> getByAttribute(String attribute,String name) {
        String sql="select * from studentcourse where "+attribute+"=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new StudentCourse(
                        rs.getString("courseClassID"),
                        rs.getString("studentID"),
                        rs.getInt("score")),
                name);
    }

    /**
     * 按照主键查询
     * @param courseClassID 课程班级ID
     * @param studentID 学生ID
     * @return 返回查询结果
     */
    public List<StudentCourse> getByCode(String courseClassID, String studentID) {
        String sql="select * from studentcourse where courseClassID=? and studentID=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new StudentCourse(
                        rs.getString("courseClassID"),
                        rs.getString("studentID"),
                        rs.getInt("score")),
                courseClassID,studentID);
    }

    /**
     * 查询studentcourse表所有数据
     * @return 返回查询结果
     */
    public List<StudentCourse> getList() {
        String sql="select * from studentcourse";
        return jdbcTemplate.query(sql,(rs, rowNum) -> new StudentCourse(
                rs.getString("courseClassID"),
                rs.getString("studentID"),
                rs.getInt("score"))
        );
    }

    /**
     * 多条件模糊查询
     * @param map 查询条件
     * @param pageIndex 起始页
     * @param pageSize 大小
     * @return 查询结果
     */
    public Map<Object, Object> query(Map<String,Object> map, int pageIndex, int pageSize){
        //给出sql模板,为了便于后面添加sql语句
        StringBuilder sql =new StringBuilder("select studentcourse.courseClassID,student.studentID,student.studentName,score,course.courseName from studentcourse,student,course,courseclass where 1=1 and student.studentID=studentcourse.studentID and course.courseID=courseclass.courseID and studentcourse.courseClassID=courseclass.courseClassID");
        //给出params
        List<Object> params = new ArrayList<>();
        //构造查询语句
        if (map.get("courseClassID")!=null)
        {
            String s = map.get("courseClassID").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and studentcourse.courseClassID like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("studentID")!=null)
        {
            String s = map.get("studentID").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and studentcourse.studentID like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("score")!=null)
        {
            String s = map.get("score").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and studentcourse.score like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("studentName")!=null)
        {
            String s = map.get("studentName").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and student.studentName like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("courseName")!=null)
        {
            String s = map.get("courseName").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and course.courseName like ?");
                params.add("%" + s + "%");
            }
        }

        //统计个数
        String sql2="SELECT count(*) as sum from ("+ sql +") as a;";
        int count=jdbcTemplate.queryForObject(sql2, Integer.class,params.toArray());
        //添加页数条目限制
        sql.append(" limit ?,?");
        params.add((pageIndex-1)*pageSize);
        params.add(pageSize);


        Map<Object, Object> response = new HashMap<>();
        response.put("total", count);
        response.put("pageIndex", pageIndex);
        response.put("tableData", jdbcTemplate.queryForList(sql.toString(),params.toArray()));

        return response;
    }
    /**
     * 多条件模糊查询（老师使用）
     * @param map 查询条件
     * @param pageIndex 起始页
     * @param pageSize 大小
     * @return 查询结果
     */
    public Map<Object, Object> queryByTeacher(Map<String,Object> map, int pageIndex, int pageSize){
        //给出sql模板,为了便于后面添加sql语句
        StringBuilder sql =new StringBuilder("select studentcourse.courseClassID,course.courseName,student.studentID,student.studentName,score from studentcourse,student,course,courseclass where 1=1 and student.studentID=studentcourse.studentID and course.courseID=courseclass.courseID and studentcourse.courseClassID=courseclass.courseClassID");
        //给出params
        List<Object> params = new ArrayList<>();
        //构造查询语句
        if (map.get("courseClassID")!=null)
        {
            String s = map.get("courseClassID").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and studentcourse.courseClassID like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("studentID")!=null)
        {
            String s = map.get("studentID").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and studentcourse.studentID like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("score")!=null)
        {
            String s = map.get("score").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and studentcourse.score like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("studentName")!=null)
        {
            String s = map.get("studentName").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and student.studentName like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("courseName")!=null)
        {
            String s = map.get("courseName").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and course.courseName like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("teacherID")!=null)
        {
            String s = map.get("teacherID").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and teacherID like ?");
                params.add("%" + s + "%");
            }
        }
        System.out.println(params);

        //统计个数
        String sql2="SELECT count(*) as sum from ("+ sql +") as a;";
        int count=jdbcTemplate.queryForObject(sql2, Integer.class,params.toArray());
        //添加页数条目限制
        sql.append(" limit ?,?");
        params.add((pageIndex-1)*pageSize);
        params.add(pageSize);


        Map<Object, Object> response = new HashMap<>();
        response.put("total", count);
        response.put("pageIndex", pageIndex);
        response.put("tableData", jdbcTemplate.queryForList(sql.toString(),params.toArray()));

        return response;
    }

}
