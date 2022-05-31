package com.lab.backend.repository;

import com.lab.backend.domain.Course;
import com.lab.backend.domain.CourseClass;
import com.lab.backend.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Map<Object, Object> query(CourseClass courseClass, int pageIndex, int pageSize) {
        //给出sql模板,为了便于后面添加sql语句
        StringBuilder sql = new StringBuilder("select * from courseClass where 1=1");
        //给出params
        List<Object> params = new ArrayList<>();
        //构造查询语句
        String courseClassID = courseClass.getCourseClassID();
        if (courseClassID != null && !courseClassID.trim().isEmpty()) {
            sql.append(" and courseClassID like ?");
            params.add("%" + courseClassID + "%");
        }
        String courseID = courseClass.getCourseID();
        if (courseID != null && !courseID.trim().isEmpty()) {
            sql.append(" and courseID like ?");
            params.add("%" + courseID + "%");
        }
        String teacherID = courseClass.getTeacherID();
        if (teacherID != null && !teacherID.trim().isEmpty()) {
            sql.append(" and teacherID like ?");
            params.add("%" + teacherID+ "%");
        }
        String courseClassTime = courseClass.getCourseClassTime();
        if (courseClassTime != null && !courseClassTime.trim().isEmpty()) {
            sql.append(" and courseClassTime like ?");
            params.add("%" + courseClassTime+ "%");
        }
        String courseClassAddress = courseClass.getCourseClassAddress();
        if (courseClassAddress != null && !courseClassAddress.trim().isEmpty()) {
            sql.append(" and courseClassAddress like ?");
            params.add("%" + courseClassAddress+ "%");
        }
        String courseClassWeek = courseClass.getCourseClassWeek();
        if (courseClassWeek != null && !courseClassWeek.trim().isEmpty()) {
            sql.append(" and courseClassWeek like ?");
            params.add("%" + courseClassWeek+ "%");
        }
        //统计个数
        String sql2 = "SELECT count(*) as sum from (" + sql + ") as a;";
        int count = jdbcTemplate.queryForObject(sql2, Integer.class, params.toArray());

        //添加页数条目限制
        sql.append(" limit ?,?");
        params.add((pageIndex - 1) * pageSize);
        params.add(pageSize);


        Map<Object, Object> response = new HashMap<>();
        response.put("total", count);
        response.put("pageIndex", pageIndex);
        response.put("tableData", jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new CourseClass(
                rs.getString("courseClassID"),
                rs.getString("courseID"),
                rs.getString("teacherID"),
                rs.getString("courseClassTime"),
                rs.getString("courseClassAddress"),
                rs.getString("courseClassWeek")
        ), params.toArray()));

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


