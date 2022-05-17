package com.lab.backend.service;

import com.lab.backend.domain.CourseClass;

import java.util.List;

public interface CourseClassService {
    int insert(CourseClass courseClass);

    int delete(String courseClassID);

    int update(CourseClass courseClass);

    List<CourseClass> query(CourseClass courseClass);

    List<CourseClass> getList();
}
