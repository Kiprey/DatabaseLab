package com.lab.backend.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="Student")
@Entity
public class Student {
    private String studentName;
    @Id
    private String studentID;
    private String className;
    private String identifier;
    private String dormitory;
    private String address;
    private String teleno;
    private String birthday;
    private String sex;
    private String grade;
    private String completedCredits;

    public Student() {

    }

    public Student(String studentName, String studentID, String className, String identifier, String dormitory, String address, String teleno, String birthday, String sex, String grade, String completedCredits) {
        this.studentName = studentName;
        this.studentID = studentID;
        this.className = className;
        this.identifier = identifier;
        this.dormitory = dormitory;
        this.address = address;
        this.teleno = teleno;
        this.birthday = birthday;
        this.sex = sex;
        this.grade = grade;
        this.completedCredits = completedCredits;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTeleno() {
        return teleno;
    }

    public void setTeleno(String teleno) {
        this.teleno = teleno;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCompletedCredits() {
        return completedCredits;
    }

    public void setCompletedCredits(String completedCredits) {
        this.completedCredits = completedCredits;
    }
}
