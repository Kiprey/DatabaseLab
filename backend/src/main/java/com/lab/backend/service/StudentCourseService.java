package com.lab.backend.service;

import com.lab.backend.domain.Course;
import com.lab.backend.domain.StudentCourse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface StudentCourseService {
    boolean insert(StudentCourse studentcourse);
    int delete(StudentCourse studentcourse);
    boolean update(StudentCourse studentcourse);
    List<StudentCourse> getByAttribute(String attribute, String name);
    List<StudentCourse> getByCode(String code);
    List<StudentCourse> getList();
}
