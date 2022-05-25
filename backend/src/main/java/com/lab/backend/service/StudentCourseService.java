package com.lab.backend.service;

import com.lab.backend.domain.StudentCourse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface StudentCourseService {
    int insert(StudentCourse studentcourse);
    int delete(String courseID);
    int update(StudentCourse studentcourse);
    List<StudentCourse> getByAttribute(String attribute, String name);
    List<StudentCourse> getByCode(String code);
    List<StudentCourse> getList();
    Map<Object, Object> query(StudentCourse studentCourse, int pageIndex, int pageSize);
}
