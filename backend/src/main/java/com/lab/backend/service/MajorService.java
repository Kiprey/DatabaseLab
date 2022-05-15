package com.lab.backend.service;

import com.lab.backend.domain.Major;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface MajorService {
    int insert(Major major);

    int delete(String majorCode);

    int update(Major major);

    List<Major> getList();

    List<Major> getListByName(String name);

    List<Major> getListByFacultyName(String facultyName);

    List<Major> getListByFacultyCode(String facultyCode);
}
