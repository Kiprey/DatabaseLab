package com.lab.backend.repository;

import com.lab.backend.domain.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class FacultyDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * 插入
     * @param faculty faculty实体
     * @return faculty实体
     */
    public Faculty insert(Faculty faculty) {
        String sql="insert into faculty values (?,?,?,?)";
        jdbcTemplate.update(sql,
                faculty.getFacultyName(),
                faculty.getFacultyCode(),
                faculty.getFacultyAddress(),
                faculty.getFacultyTeleno());
        return faculty;
    }

    /**
     * 删除
     */
    public void delete(String facultyCode) {
        String sql="delete from Faculty where facultyCode = ?";
        jdbcTemplate.update(sql, facultyCode);
    }

    /**
     * 更新
     */
    public void update(Faculty faculty) {
        String sql="UPDATE faculty SET " +
                "facultyName=?, " +
                "facultyCode=?, " +
                "facultyAddress=?, " +
                "facultyTeleno=?, " +
                "WHERE facultyId=?";
        jdbcTemplate.update(sql,
                faculty.getFacultyName(),
                faculty.getFacultyCode(),
                faculty.getFacultyAddress(),
                faculty.getFacultyTeleno());
    }

    /**
     * 按username查询
     */
    public List<Faculty> getByUsername(String username) {
        String sql="select * from faculty where facultyName=?";
        return jdbcTemplate.query(sql, new RowMapper<Faculty>() {
            @Override
            public Faculty mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Faculty(
                        rs.getString("facultyName"),
                        rs.getString("facultyCode"),
                        rs.getString("facultyAddress"),
                        rs.getString("facultyTeleno")
                );
            }
        },username);
    }

    /**
     * 列表查看
     */
    public List<Faculty> getList() {
        String sql="select * from faculty";
        return jdbcTemplate.query(sql, new RowMapper<Faculty>() {
            @Override
            public Faculty mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Faculty(
                        rs.getString("facultyName"),
                        rs.getString("facultyCode"),
                        rs.getString("facultyAddress"),
                        rs.getString("facultyTeleno")
                );
            }
        });
    }
}
