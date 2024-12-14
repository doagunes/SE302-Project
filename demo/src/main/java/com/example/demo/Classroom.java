package com.example.demo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Classroom {
    private String classroomName;
    private int capacity;
    //private ArrayList<Boolean> availableList;
    private ArrayList<Course> courses;
    private ClassroomDataAccessObject classroomDAO;

    //TODO: BURDA TÜM CLASSROOMLAR CHECK EDİLİYOR.
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

    public ArrayList<Classroom> getAllClassrooms() {
        return classroomDAO.getClassrooms();
    }

    public int getCapacity() {
        return classroomDAO.getCapacityWhereClassroomIs(classroomName);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }


}
