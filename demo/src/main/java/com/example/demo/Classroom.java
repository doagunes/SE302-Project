package com.example.demo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Classroom implements IGeneric {
    private String classroomName;
    private int capacity;
    private ArrayList<Boolean> availableList;
    private ArrayList<Course> courses;

    public boolean isAvailable(LocalTime startTime, LocalTime endTime) {
        for (Course course : courses) {
            LocalTime courseStartTime = course.getStartTime();
            LocalTime courseEndTime = course.getEndTime(courseStartTime);

            if ((startTime.isBefore(courseEndTime) && endTime.isAfter(courseStartTime))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Classroom add() {
        return null;
    }

    @Override
    public Classroom remove() {
        return null;
    }

    @Override
    public Classroom update() {
        return null;
    }
}
