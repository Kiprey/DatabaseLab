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
     * @param course 课程实体
     * @param pageIndex 起始页
     * @param pageSize 大小
     * @return 查询结果
     */
    public Map<Object, Object> query(Course course, int pageIndex, int pageSize){
        //给出sql模板,为了便于后面添加sql语句
        StringBuilder sql =new StringBuilder("select * from course where 1=1");
        //给出params
        List<Object> params = new ArrayList<>();
        //构造查询语句
        String courseName = course.getCourseName();
        if(courseName != null && !courseName.trim().isEmpty()){
            sql.append(" and courseName like ?");
            params.add("%" +courseName+ "%");
        }
        String courseNature= course.getCourseNature();
        if(courseNature != null && !courseNature.trim().isEmpty()){
            sql.append(" and courseNature like ?");
            params.add("%" +courseNature+ "%");
        }

        String courseCategory= course.getCourseCategory();
        if(courseCategory != null && !courseCategory.trim().isEmpty()){
            sql.append(" and courseCategory like ?");
            params.add("%" +courseCategory+ "%");
        }

        String courseID= course.getCourseID();
        if(courseID != null && !courseID.trim().isEmpty()){
            sql.append(" and courseID like ?");
            params.add("%" +courseID+ "%");
        }

        String facultyCode= course.getFacultyCode();
        if(facultyCode != null && !facultyCode.trim().isEmpty()){
            sql.append(" and facultyCode like ?");
            params.add("%" +facultyCode+ "%");
        }

        Integer courseHours= course.getCourseHours();
        if(courseHours != null){
            sql.append(" and courseHours like ?");
            params.add("%" +courseHours+ "%");
        }

        Integer credit= course.getCredit();
        if(credit != null){
            sql.append(" and credit like ?");
            params.add("%" +credit+ "%");
        }

        //添加页数条目限制
        sql.append(" limit ?,?");
        params.add((pageIndex-1)*30);
        params.add(pageSize);
        //统计个数
        String sql2="SELECT count(*) as sum from ("+ sql +") as a;";
        System.out.println(sql2);
        int count=jdbcTemplate.queryForObject(sql2, Integer.class,params.toArray());

        Map<Object, Object> response=new HashMap<>();
        response.put("total",count);
        response.put("pageIndex",pageIndex);
        response.put("tableData",jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new Course(
                rs.getString("coursename"),
                rs.getString("courseNature"),
                rs.getString("courseCategory"),
                rs.getString("courseID"),
                rs.getString("facultyCode"),
                rs.getInt("courseHours"),
                rs.getInt("credit")
        ),params.toArray()));

        return response;
    }
}
