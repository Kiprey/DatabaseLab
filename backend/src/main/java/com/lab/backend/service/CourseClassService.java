package com.lab.backend.service;

import com.lab.backend.domain.CourseClass;

import java.util.List;
import java.util.Map;

public interface CourseClassService {
    int insert(CourseClass courseClass);

    int delete(String courseClassID);

    int update(CourseClass courseClass);


    List<CourseClass> getList();
    Map<Object, Object> query(CourseClass courseClass, int pageIndex, int pageSize);
}
