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
    private String classCode;
    private String identifier;
    private String dormitory;
    private String address;
    private String teleno;
    private String birthday;
    private String sex;
    private String grade;
    private Integer completedCredits;

    public Student() {

    }

    public Student(String studentName, String studentID, String classCode, String identifier, String dormitory, String address, String teleno, String birthday, String sex, String grade, Integer completedCredits) {
        this.studentName = studentName;
        this.studentID = studentID;
        this.classCode = classCode;
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

    public String getStudentID() {
        return studentID;
    }

    public String getClassCode() {
        return classCode;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getDormitory() {
        return dormitory;
    }

    public String getAddress() {
        return address;
    }

    public String getTeleno() {
        return teleno;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getSex() {
        return sex;
    }

    public String getGrade() {
        return grade;
    }

    public Integer getCompletedCredits() {
        return completedCredits;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTeleno(String teleno) {
        this.teleno = teleno;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setCompletedCredits(Integer completedCredits) {
        this.completedCredits = completedCredits;
    }
}
