package com.example.demo;

import com.almasb.fxgl.core.collection.Array;

import java.time.LocalTime;
import java.util.ArrayList;

public class Course implements IGeneric {
    private String courseName;
    private int courseID;
    private int duration;
    private LocalTime startTime;
    private LocalTime endTime;
    private Lecturer lecturer;
    private Classroom assignedClassroom;
    private ArrayList<Student> enrolledStudentsList;
    private ArrayList<Attendance> attendanceRecordList;


    public Course(String courseName, int courseID, int duration, LocalTime startTime, LocalTime endTime,
                  Lecturer lecturer) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = this.getEndTime(startTime);
        this.lecturer = lecturer;
    }

    public LocalTime getEndTime(LocalTime startTime) {
        int totalDuration = duration*45 + (duration - 1)*10;
        endTime = startTime.plusMinutes(totalDuration);
        return endTime;
    }

    @Override
    public Course add() {
        return null;
    }

    @Override
    public Course remove() {
        return null;
    }

    @Override
    public Course update(Object obj) {
        return null;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
}
