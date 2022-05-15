package com.lab.backend.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "CourseClass")
@Entity
public class CourseClass {
    @Id
    private String courseClassID;
    private String courseID;
    private String teacherID;
    private String courseClassTime;
    private String courseClassAddress;
    private String courseClassWeek;


    public CourseClass() {
    }

    public CourseClass(String courseClassID, String courseID, String teacherID) {
        this.courseClassID = courseClassID;
        this.courseID = courseID;
        this.teacherID = teacherID;
    }

    public CourseClass(String courseClassID, String courseID, String teacherID, String courseClassTime, String courseClassAddress, String courseClassWeek) {
        this.courseClassID = courseClassID;
        this.courseID = courseID;
        this.teacherID = teacherID;
        this.courseClassTime = courseClassTime;
        this.courseClassAddress = courseClassAddress;
        this.courseClassWeek = courseClassWeek;
    }

    public String getCourseClassID() {
        return courseClassID;
    }

    public void setCourseClassID(String courseClassID) {
        this.courseClassID = courseClassID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getCourseClassTime() {
        return courseClassTime;
    }

    public void setCourseClassTime(String courseClassTime) {
        this.courseClassTime = courseClassTime;
    }

    public String getCourseClassAddress() {
        return courseClassAddress;
    }

    public void setCourseClassAddress(String courseClassAddress) {
        this.courseClassAddress = courseClassAddress;
    }

    public String getCourseClassWeek() {
        return courseClassWeek;
    }

    public void setCourseClassWeek(String courseClassWeek) {
        this.courseClassWeek = courseClassWeek;
    }
}
