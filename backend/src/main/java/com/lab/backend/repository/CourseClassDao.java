package com.lab.backend.repository;

import com.lab.backend.domain.Course;
import com.lab.backend.domain.CourseClass;
import com.lab.backend.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class CourseClassDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 插入
     *
     * @param courseClass 课程班级实体
     */
    public void insert(CourseClass courseClass) {
        String sql = "insert into CourseClass values (?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                courseClass.getCourseClassID(),
                courseClass.getCourseID(),
                courseClass.getTeacherID(),
                courseClass.getCourseClassTime(),
                courseClass.getCourseClassAddress(),
                courseClass.getCourseClassWeek());
    }

    /**
     * 删除
     *
     * @param courseClassID 开课号
     */
    public void delete(String courseClassID) {
        String sql = "delete from CourseClass where courseClassID = ?";
        jdbcTemplate.update(sql, courseClassID);
    }

    /**
     * 更新
     *
     * @param courseClass 课程班级实体
     */
    public void update(CourseClass courseClass) {
        String sql = "UPDATE CourseClass SET courseID=?,teacherID=?,courseClassTime=?,courseClassAddress=?,courseClassWeek=? WHERE courseClassID=?";
        jdbcTemplate.update(sql,
                courseClass.getCourseID(),
                courseClass.getTeacherID(),
                courseClass.getCourseClassTime(),
                courseClass.getCourseClassAddress(),
                courseClass.getCourseClassWeek(),
                courseClass.getCourseClassID());
    }

    public Map<Object, Object> query(Map<String,Object> map, int pageIndex, int pageSize) {
        //给出sql模板,为了便于后面添加sql语句
        StringBuilder sql = new StringBuilder("select courseClassID,courseclass.courseID,courseName,courseclass.teacherID,teacherName,facultyName,courseClassTime,courseClassAddress,courseClassWeek,courseNature,courseCategory,courseHours,credit from courseClass,teacher,course,faculty  where 1=1 and courseclass.teacherID=teacher.teacherID and courseclass.courseID=course.courseID and course.facultyCode=faculty.facultyCode");
        //给出params
        List<Object> params = new ArrayList<>();
        //构造查询语句
        if (map.get("courseClassID")!=null)
        {
            String s = map.get("courseClassID").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and courseClassID like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("courseID")!=null)
        {
            String s = map.get("courseID").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and courseclass.courseID like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("courseName")!=null)
        {
            String s = map.get("courseName").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and courseName like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("teacherID")!=null)
        {
            String s = map.get("teacherID").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and courseclass.teacherID like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("teacherName")!=null)
        {
            String s = map.get("teacherName").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and teacherName like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("facultyName")!=null)
        {
            String s = map.get("facultyName").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and facultyName like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("courseClassTime")!=null)
        {
            String s = map.get("courseClassTime").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and courseClassTime like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("courseClassAddress")!=null)
        {
            String s = map.get("courseClassAddress").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and courseClassAddress like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("courseClassWeek")!=null)
        {
            String s = map.get("courseClassWeek").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and courseClassWeek like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("courseNature")!=null)
        {
            String s = map.get("courseNature").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and courseNature like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("courseCategory")!=null)
        {
            String s = map.get("courseCategory").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and courseCategory like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("courseHours")!=null)
        {
            Integer s = (Integer) map.get("credit");
            if (s != null)
            {
                sql.append(" and courseHours like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("credit")!=null)
        {
            Integer s = (Integer) map.get("credit");
            if (s != null)
            {
                sql.append(" and credit like ?");
                params.add("%" + s + "%");
            }
        }
        //统计个数
        String sql2 = "SELECT count(*) as sum from (" + sql + ") as a;";
        System.out.println(sql);
        int count = jdbcTemplate.queryForObject(sql2, Integer.class, params.toArray());

        //添加页数条目限制
        sql.append(" limit ?,?");
        params.add((pageIndex - 1) * pageSize);
        params.add(pageSize);


        Map<Object, Object> response = new HashMap<>();
        response.put("total", count);
        response.put("pageIndex", pageIndex);
        response.put("tableData", jdbcTemplate.queryForList(sql.toString(),params.toArray()));

        return response;
    }
    public Map<Object, Object> queryByUser(Map<String,Object> map, int pageIndex, int pageSize) {
        //给出sql模板,为了便于后面添加sql语句
        StringBuilder sql = new StringBuilder("select courseClassID,courseclass.courseID,courseName,teacherName,facultyName,courseClassTime,courseClassAddress,courseClassWeek,courseNature,courseCategory,courseHours,credit from courseClass,teacher,course,faculty  where 1=1 and courseclass.teacherID=teacher.teacherID and courseclass.courseID=course.courseID and course.facultyCode=faculty.facultyCode");
        //给出params
        List<Object> params = new ArrayList<>();
        //构造查询语句
        if (map.get("courseClassID")!=null)
        {
            String s = map.get("courseClassID").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and courseClassID like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("courseID")!=null)
        {
            String s = map.get("courseID").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and courseclass.courseID like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("courseName")!=null)
        {
            String s = map.get("courseName").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and courseName like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("teacherName")!=null)
        {
            String s = map.get("teacherName").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and teacherName like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("facultyName")!=null)
        {
            String s = map.get("facultyName").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and facultyName like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("courseClassTime")!=null)
        {
            String s = map.get("courseClassTime").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and courseClassTime like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("courseClassAddress")!=null)
        {
            String s = map.get("courseClassAddress").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and courseClassAddress like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("courseClassWeek")!=null)
        {
            String s = map.get("courseClassWeek").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and courseClassWeek like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("courseNature")!=null)
        {
            String s = map.get("courseNature").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and courseNature like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("courseCategory")!=null)
        {
            String s = map.get("courseCategory").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and courseCategory like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("courseHours")!=null)
        {
            Integer s = (Integer) map.get("credit");
            if (s != null)
            {
                sql.append(" and courseHours like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("credit")!=null)
        {
            Integer s = (Integer) map.get("credit");
            if (s != null)
            {
                sql.append(" and credit like ?");
                params.add("%" + s + "%");
            }
        }
        //统计个数
        String sql2 = "SELECT count(*) as sum from (" + sql + ") as a;";
        System.out.println(sql);
        int count = jdbcTemplate.queryForObject(sql2, Integer.class, params.toArray());

        //添加页数条目限制
        sql.append(" limit ?,?");
        params.add((pageIndex - 1) * pageSize);
        params.add(pageSize);


        Map<Object, Object> response = new HashMap<>();
        response.put("total", count);
        response.put("pageIndex", pageIndex);
        response.put("tableData", jdbcTemplate.queryForList(sql.toString(),params.toArray()));

        return response;
    }
    /**
     * 按courseClassID字段查询
     *
     * @param code courseClassID字段的值
     * @return 返回查询结果
     */
    public List<CourseClass> getByCode(String code) {
        String sql = "select * from courseclass where courseClassID=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new CourseClass(
                        rs.getString("courseClassID"),
                        rs.getString("courseID"),
                        rs.getString("teacherID"),
                        rs.getString("courseClassTime"),
                        rs.getString("courseClassAddress"),
                        rs.getString("courseClassWeek"))
                , code);
    }

    /**
     * 按courseClass字段查询
     *
     * @param attribute 字段名
     * @param name      字段的值
     * @return 返回查询结果
     */
    public List<CourseClass> getByAttribute(String attribute, String name) {
        String sql = "select * from courseClass where " + attribute + "=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new CourseClass(
                        rs.getString("courseClassID"),
                        rs.getString("courseID"),
                        rs.getString("teacherID"),
                        rs.getString("courseClassTime"),
                        rs.getString("courseClassAddress"),
                        rs.getString("courseClassWeek"))
                , name);
    }

    /**
     * 列表查看（全部查询）
     */
    public List<CourseClass> getList() {
        String sql = "select * from CourseClass";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new CourseClass(
                rs.getString("courseClassID"),
                rs.getString("courseID"),
                rs.getString("teacherID"),
                rs.getString("courseClassTime"),
                rs.getString("courseClassAddress"),
                rs.getString("courseClassWeek")
        ));
    }
}


