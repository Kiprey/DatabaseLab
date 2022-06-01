package com.lab.backend.repository;


import com.lab.backend.domain.Faculty;
import com.lab.backend.domain.Major;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class MajorDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
//    @Resource

    /**
     * 插入
     * @param major 专业实体
     */
    public void insert(Major major) {
        String sql="insert into major values (?,?,?,?,?)";
        jdbcTemplate.update(sql,
                major.getMajorName(),
                major.getMajorCode(),
                major.getFacultyCode(),
                major.getDegreeLevel(),
                major.getGraduationCredits());
    }

    /**
     * 删除
     * @param majorCode 专业代码
     */
    public void delete(String majorCode) {
        String sql="delete from major where majorCode = ?";
        jdbcTemplate.update(sql, majorCode);
    }

    /**
     * 更新
     * @param major 专业实体
     */
    public void update(Major major) {
        String sql="UPDATE major SET majorName=?,facultyCode=?, degreeLevel=? ,graduationCredits=? WHERE majorCode=?";
        jdbcTemplate.update(sql,
                major.getMajorName(),
                major.getFacultyCode(),
                major.getDegreeLevel(),
                major.getGraduationCredits(),
                major.getMajorCode());
    }

    /**
     * 按某一个属性查询
     * @param attribute 要查询的属性
     * @param name 要查询的属性值
     * @return 查询结果
     */
    public List<Major> getByAttribute(String attribute, String name) {
        String sql="select * from major where "+attribute+" like ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Major(
                rs.getString("majorName"),
                rs.getString("majorCode"),
                rs.getString("facultyCode"),
                rs.getString("degreeLevel"),
                rs.getString("graduationCredits")
        ),name);
    }

    /**
     * 按Code查询
     */
    public List<Major> getByCode(String code) {
        String sql="select * from major where majorCode=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Major(
                rs.getString("majorName"),
                rs.getString("majorCode"),
                rs.getString("facultyCode"),
                rs.getString("degreeLevel"),
                rs.getString("graduationCredits")
        ),code);
    }


    /**
     * 多条件模糊查询
     * @param major 专业实体
     * @param pageIndex 起始页
     * @param pageSize  每页个数
     * @return result
     */
    public Map<Object, Object> query(Major major,String facultyName,int pageIndex, int pageSize){
        //给出sql模板,为了便于后面添加sql语句
        StringBuilder sql =new StringBuilder("select faculty.facultyCode,facultyName,major.majorCode,majorName,degreeLevel,graduationCredits from major,faculty where major.facultyCode=faculty.facultyCode");
        //给出params
        List<Object> params = new ArrayList<>();
        //构造查询语句


        String majorName = major.getMajorName();
        if(majorName != null && !majorName.trim().isEmpty()){
            sql.append(" and majorName like ?");
            params.add("%" +majorName+ "%");
        }
        String majorCode= major.getMajorCode();
        if(majorCode != null && !majorCode.trim().isEmpty()){
            sql.append(" and majorCode like ?");
            params.add("%" +majorCode+ "%");
        }

        String facultyCode= major.getFacultyCode();
        if(facultyCode != null && !facultyCode.trim().isEmpty()){
            sql.append(" and major.facultyCode like ?");
            params.add("%" +facultyCode+ "%");
        }

        String degreeLevel= major.getDegreeLevel();
        if(degreeLevel != null && !degreeLevel.trim().isEmpty()){
            sql.append(" and degreeLevel like ?");
            params.add("%" +degreeLevel+ "%");
        }
        String graduationCredits= major.getGraduationCredits();
        if(graduationCredits != null && !graduationCredits.trim().isEmpty()){
            sql.append(" and graduationCredits like ?");
            params.add("%" +graduationCredits+ "%");
        }

        if(facultyName != null && !facultyName.trim().isEmpty()){
            sql.append(" and facultyName like ?");
            params.add("%" +facultyName+ "%");
        }

        //统计个数
        String sql2="SELECT count(*) as sum from ("+ sql +") as a;";
        int count=jdbcTemplate.queryForObject(sql2, Integer.class,params.toArray());
        //添加页数条目限制
        sql.append(" limit ?,?");
        params.add((pageIndex-1)*pageSize);
        params.add(pageSize);



        Map<Object, Object> response=new HashMap<>();
        response.put("total",count);
        response.put("pageIndex",pageIndex);
        response.put("tableData",jdbcTemplate.queryForList(sql.toString(), params.toArray()));

        return response;
    }
}
