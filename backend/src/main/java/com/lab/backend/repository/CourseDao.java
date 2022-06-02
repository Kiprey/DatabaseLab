package com.lab.backend.repository;

import com.lab.backend.domain.Course;
import com.lab.backend.domain.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class CourseDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 插入
     * @param course 课程实体
     */
    public void insert(Course course) {
        String sql="insert into course values (?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                course.getCourseName(),
                course.getCourseNature(),
                course.getCourseCategory(),
                course.getCourseID(),
                course.getFacultyCode(),
                course.getCourseHours(),
                course.getCredit());
    }

    /**
     * 按照主键删除
     * @param CourseID CourseID字段
     */
    public void delete(String CourseID) {
        String sql="delete from course where CourseID = ?";
        jdbcTemplate.update(sql, CourseID);
    }

    /**
     * 按照主键更新
     * @param course 课程实体
     */
    public void update(Course course) {
        String sql="UPDATE course SET coursename=?," +
                "courseNature=?, " +
                "courseCategory=?," +
                "facultyCode=?," +
                "courseHours=?," +
                "credit=? WHERE courseID=?";
        jdbcTemplate.update(sql,
                course.getCourseName(),
                course.getCourseNature(),
                course.getCourseCategory(),
                course.getFacultyCode(),
                course.getCourseHours(),
                course.getCredit(),
                course.getCourseID());
    }

    /**
     * 按字段查询
     * @param attribute 字段名
     * @param name 字段值
     * @return 返回查询结果
     */
    public List<Course> getByAttribute(String attribute, String name) {
        String sql="select * from course where " + attribute + "=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Course(
                rs.getString("coursename"),
                rs.getString("courseNature"),
                rs.getString("courseCategory"),
                rs.getString("courseID"),
                rs.getString("facultyCode"),
                rs.getInt("courseHours"),
                rs.getInt("credit"))
                ,name);
    }

    /**
     * 按courseID字段查询
     * @param code courseID字段的值
     * @return 返回查询结果
     */
    public List<Course> getByCode(String code) {
        String sql="select * from course where courseID=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Course(
                rs.getString("coursename"),
                rs.getString("courseNature"),
                rs.getString("courseCategory"),
                rs.getString("courseID"),
                rs.getString("facultyCode"),
                rs.getInt("courseHours"),
                rs.getInt("credit")),
                code);
    }

    /**
     * 查询course表所有数据
     * @return 返回查询结果
     */
    public List<Course> getList() {
        String sql="select * from course";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Course(
                rs.getString("coursename"),
                rs.getString("courseNature"),
                rs.getString("courseCategory"),
                rs.getString("courseID"),
                rs.getString("facultyCode"),
                rs.getInt("courseHours"),
                rs.getInt("credit"))
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
        StringBuilder sql =new StringBuilder("select facultyName,courseID,courseName,courseNature,courseCategory,courseHours,credit from course,faculty where 1=1 and course.facultyCode=faculty.facultyCode");
        //给出params
        List<Object> params = new ArrayList<>();
        //构造查询语句
        if (map.get("courseName")!=null)
        {
            String s = map.get("courseName").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and courseName like ?");
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
        if (map.get("courseID")!=null)
        {
            String s = map.get("courseID").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and courseID like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("facultyCode")!=null)
        {
            String s = map.get("facultyCode").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and course.facultyCode like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("courseHours")!=null)
        {
            String s = map.get("courseHours").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and course.courseHours like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("credit")!=null)
        {
            String s = map.get("credit").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and course.credit like ?");
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
