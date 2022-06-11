package com.lab.backend.service;

import com.lab.backend.domain.Teacher;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface TeacherService {
    int insert(Teacher teacher);

    int delete(String teacherID);

    int update(Teacher teacher);

    Map<Object, Object> query(Map<String, Object> map, int pageIndex, int pageSize);

    Map<Object, Object> queryWithoutID(Map<String, Object> map, int pageIndex, int pageSize);

    List<Teacher> getList();

    List<Teacher> info();
}
