package com.lab.backend.service;

import com.lab.backend.domain.Classes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ClassService {
    int insert(Classes classes);

    int delete(String classCode);

    int update(Classes classes);

    List<Classes> getList();

    List<Classes> getListByMajorName(String MajorName);

    Map<Object, Object> query(Classes classes, int pageIndex, int pageSize);
}
