package com.lab.backend.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="person")
@Entity
public class Person {
    private String username;
    @Id
    private String name;
    private Integer age;
    private String teleno;

    public Person(){

    }
    public Person(String username,String name,Integer age,String teleno){
        setUsername(username);
        setName(name);
        setAge(age);
        setTeleno(teleno);
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTeleno() {
        return teleno;
    }

    public void setTeleno(String teleno) {
        this.teleno = teleno;
    }
}
