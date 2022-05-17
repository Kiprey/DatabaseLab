package com.lab.backend.service;

import com.lab.backend.domain.Classes;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ClassService {
    int insert(Classes classes);

    int delete(String classCode);

    int update(Classes classes);

    List<Classes> getList();

    List<Classes> getListByName(String name);

    List<Classes> getListByMajorName(String MajorName);

    List<Classes> getListByMajorCode(String majorCode);
}
