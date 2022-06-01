package com.lab.backend.service;

import com.lab.backend.domain.Major;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface MajorService {
    int insert(Major major);

    int delete(String majorCode);

    int update(Major major);


    Map<Object, Object> query(Major major, String facultyName, int pageIndex, int pageSize);
}
