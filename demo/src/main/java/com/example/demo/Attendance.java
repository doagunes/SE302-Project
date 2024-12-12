package com.example.demo;

import java.time.LocalTime;

public class Attendance {

    private LocalTime time;
    private String courseDay;
    private boolean isPresent;
    private Student student;
    private Course course;

    public Attendance(boolean isPresent, LocalTime time, String courseDay, Student student, Course course) {
        this.isPresent = isPresent;
        this.time = time;
        this.student = student;
        this.course = course;
    }


    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
