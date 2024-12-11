package com.example.demo;

import com.example.demo.CSV_Reader;
import com.example.demo.Classroom;
import com.example.demo.Course;

import java.io.IOException;
import java.util.ArrayList;

public class İlaydaTest {
    public static void main(String[] args) {
        CSV_Reader reader = new CSV_Reader();

        try {
            // Dersleri ve sınıfları CSV'den okuma
            ArrayList<Course> courses = reader.readCourses();
            ArrayList<Classroom> classrooms = reader.readClassrooms();

            // Her ders için uygun sınıfı atama
            for (Course course : courses) {
                System.out.println(course.getCourseID() + " " + course.getStudentNames().size());
                course.assignClassroom(classrooms);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
