package com.lab.backend.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="StudentCourse")
@Entity
public class StudentCourse {
    @Id
    private String courseClassID;
    private String studentID;
    private int score;

    public StudentCourse(String courseClassID, String studentID, int score) {
        this.courseClassID = courseClassID;
        this.studentID = studentID;
        this.score = score;
    }

    public String getCourseClassID() {
        return courseClassID;
    }

    public void setCourseClassID(String courseClassID) {
        this.courseClassID = courseClassID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
