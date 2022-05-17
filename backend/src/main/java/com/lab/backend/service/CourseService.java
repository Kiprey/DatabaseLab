package com.lab.backend.service;

import com.lab.backend.domain.Course;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CourseService {
    int insert(Course course);
    int delete(String courseID);
    int update(Course course);
    List<Course> getByAttribute(String attribute, String name);
    List<Course> getByCode(String code);
    List<Course> getList();
}
