package com.lab.backend.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "Class")
@Entity
public class Classes {
    private String className;
    @Id
    private String classCode;
    private String majorCode;

    public Classes() {
    }

    public Classes(String className, String classCode, String majorCode) {
        this.className = className;
        this.classCode = classCode;
        this.majorCode = majorCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassCode() {
        return classCode;
    }
    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getMajorCode() {
        return majorCode;
    }

    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }
}
