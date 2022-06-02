package com.lab.backend.service;

import com.lab.backend.domain.StudentCourse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface StudentCourseService {
    int insert(StudentCourse studentcourse);
    int delete(String courseClassID,String studentID);
    int update(StudentCourse studentcourse);
    List<StudentCourse> getByAttribute(String attribute, String name);
    List<StudentCourse> getByCode(String courseClassID, String studentID);
    List<StudentCourse> getList();
    Map<Object, Object> query(Map<String,Object> map, int pageIndex, int pageSize);
    List<Integer> getMeanScore(String studentID);
    int insertByStudent(String courseClassID);
    int deleteByStudent(String courseClassID);
    int updateByTeacher(StudentCourse studentcourse);
    Map<Object, Object> queryByStudent(Map<String,Object> map, int pageIndex, int pageSize);
    Map<Object, Object> queryByTeacher(Map<String,Object> map, int pageIndex, int pageSize);
}
