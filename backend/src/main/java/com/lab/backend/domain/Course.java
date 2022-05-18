package com.lab.backend.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="Course")
@Entity
public class Course {
    private String courseName;
    private String courseNature;
    private String courseCategory;
    @Id
    private String courseID;
    private String facultyCode;
    private int courseHours;
    private int credit;

    public Course(String courseName, String courseNature, String courseCategory, String courseID, String facultyCode, int courseHours, int credit) {
        this.courseName = courseName;
        this.courseNature = courseNature;
        this.courseCategory = courseCategory;
        this.courseID = courseID;
        this.facultyCode = facultyCode;
        this.courseHours = courseHours;
        this.credit = credit;
    }

    public Course() {

    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNature() {
        return courseNature;
    }

    public void setCourseNature(String courseNature) {
        this.courseNature = courseNature;
    }

    public String getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(String courseCategory) {
        this.courseCategory = courseCategory;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getFacultyCode() {
        return facultyCode;
    }

    public void setFacultyCode(String facultyCode) {
        this.facultyCode = facultyCode;
    }

    public int getCourseHours() {
        return courseHours;
    }

    public void setCourseHours(int courseHours) {
        this.courseHours = courseHours;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}
