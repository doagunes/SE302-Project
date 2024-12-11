package com.example.demo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Classroom implements IGeneric {
    private String classroomName;
    private int capacity;
    private ArrayList<Boolean> availableList;
    private ArrayList<Course> courses;

    public boolean isAvailable(String day, LocalTime startTime, LocalTime endTime) {
        // Eğer 'courses' listesi null ise bu sınıf uygundur.
        if (this.courses == null || this.courses.isEmpty()) {
            return true;
        }

        for (Course course : courses) {
            LocalTime courseStartTime = course.getStartTime();
            LocalTime courseEndTime = course.getEndTime(courseStartTime);
            String courseDay = course.getCourseDay();
            if (courseDay.equals(day)) {
                if (startTime.isBefore(courseEndTime) && endTime.isAfter(courseStartTime)) {
                    return false; // Eğer çakışma varsa, bu sınıf uygun değil
                }
            }
        }
        return true;
    }

    public Classroom(String classroomName, int capacity) {
        this.classroomName = classroomName;
        this.capacity = capacity;
        this.courses = new ArrayList<>();
    }



    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Boolean> getAvailableList() {
        return availableList;
    }

    public void setAvailableList(ArrayList<Boolean> availableList) {
        this.availableList = availableList;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    @Override
    public void add() {}

    @Override
    public void remove() {}

    @Override
    public Classroom update(Object obj) {
        return null;
    }

}
