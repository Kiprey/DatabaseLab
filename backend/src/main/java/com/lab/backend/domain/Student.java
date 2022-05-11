package com.lab.backend.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="学生")
@Entity
public class Student {
    private String name;
    @Id
    private String sno;
    private String cno;

    public Student() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public Student(String name, String sno, String cno) {
        this.name = name;
        this.sno = sno;
        this.cno = cno;
    }
}
