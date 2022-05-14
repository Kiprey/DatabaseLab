package com.lab.backend.repository;

import com.lab.backend.domain.Faculty;
import com.lab.backend.domain.Major;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class MajorDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        String sql="select * from major where "+attribute+"=?";
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
     * 列表查看
     */
    public List<Major> getList() {
        String sql="select * from major";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Major(
                rs.getString("majorName"),
                rs.getString("majorCode"),
                rs.getString("facultyCode"),
                rs.getString("degreeLevel"),
                rs.getString("graduationCredits")
        ));
    }
}
