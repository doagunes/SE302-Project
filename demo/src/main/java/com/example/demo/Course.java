package com.example.demo;

import java.util.ArrayList;

public class Course implements IGeneric {
    private String courseName;
    private int courseID;
    private Lecturer lecturer;
    private Classroom assignedClassroom;
    private ArrayList<Student> enrolledStudentsList;
    private ArrayList<Attendance> attendanceRecordList;


    public Course() {

    }

    @Override
    public Object add() {
        return null;
    }

    @Override
    public Object remove() {
        return null;
    }

    @Override
    public Object update() {
        return null;
    }
}
