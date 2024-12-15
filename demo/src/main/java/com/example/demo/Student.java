package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Student extends Person{
    private static Map<String, Student> studentsByName = new HashMap<>();
    private Map<Course, Integer> absenceCountByCourse = new HashMap<>();  // Her dersin devamsızlık sayısı

    public Student(String name) {
        super(name);
    }

    public static Student findStudentByName(String name) {
        // Eğer öğrenci varsa, döndür
        if (studentsByName.containsKey(name)) {
            return studentsByName.get(name);
        }

        // Aksi takdirde yeni bir öğrenci oluştur ve map'e ekle
        Student student = new Student(name);
        studentsByName.put(name, student);
        return student;
    }

    public int getAbsenceCountForCourse(Course course) {
        //TODO SQL'dan çekilcek veri
        return absenceCountByCourse.getOrDefault(course, 0);
    }

    public void incrementAbsenteeismForCourse(Course course) {
        int currentCount = absenceCountByCourse.getOrDefault(course, 0);
        absenceCountByCourse.put(course, currentCount + 1);
    }

    public void markAttendanceForCourse(Course course, boolean isPresent) {
        Attendance attendance = new Attendance(isPresent, course.getStartTime(), course.getCourseDay(), this, course);
        if (!isPresent) {
            incrementAbsenteeismForCourse(course);  // Devamsızlık olduğunda sayıyı artır
        }
        course.getAttendanceRecordList().add(attendance);  // Attendance kaydını derse ekle
    }


    public boolean isAvaiable(Course course){
        for(Course c : getCourses() ){
            if(c.getCourseDay() == course.getCourseDay()){
                if(c.getEndTime().isAfter(course.getStartTime())){
                    return true;
                }else if(c.getStartTime().isAfter(course.getEndTime())){
                    return true;
                }
            }
        }
       return false;
    }
}
