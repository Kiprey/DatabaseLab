package com.lab.backend.service;

import com.lab.backend.domain.Course;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CourseService {
    boolean insert(Course course);
    int delete(Course course);
    boolean update(Course course);
    List<Course> getByAttribute(String attribute, String name);
    List<Course> getByCode(String code);
    List<Course> getList();
}
