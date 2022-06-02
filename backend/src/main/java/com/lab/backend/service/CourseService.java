package com.lab.backend.service;

import com.lab.backend.domain.Course;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CourseService {
    int insert(Course course);
    int delete(String courseID);
    int update(Course course);
    List<Course> getByAttribute(String attribute, String name);
    List<Course> getByCode(String code);
    List<Course> getList();
    Map<Object, Object> query(Map<String,Object> map, int pageIndex, int pageSize);
}
