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
            Lecturer lecturer = null;
            for (Course course : courses) {
                if(course.getLecturerName().equals("İlker Korkmaz")) {
                    lecturer = course.getLecturer();
                }
            }
            for(Course crs : lecturer.getCourses()) {
                System.out.println(crs.getCourseID());
            }
            Course crs1 = lecturer.getCourses().get(1);
            Course crs2 = lecturer.getCourses().get(2);
            Course crs3 = courses.get(3);
            Course crs4 = courses.get(3);


           // lecturer.TransferStudentToAnotherCourse(crs1, crs2, crs1.getEnrolledStudentsList().get(1));
            



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
