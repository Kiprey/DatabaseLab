package com.lab.backend.service;

import com.lab.backend.domain.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {
    int insert(Person person);
    List<Person> getList();
    List<Person> getByUsername(String username);
}
