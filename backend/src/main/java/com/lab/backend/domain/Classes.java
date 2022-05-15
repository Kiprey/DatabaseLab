package com.lab.backend.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "Class")
@Entity
public class Classes {
    @Id
    private String className;
    private String majorCode;

    public Classes() {
    }

    public Classes(String className, String majorCode) {
        this.className = className;
        this.majorCode = majorCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMajorCode() {
        return majorCode;
    }

    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }
}
