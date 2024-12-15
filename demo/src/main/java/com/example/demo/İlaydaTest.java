package com.example.demo;

import com.example.demo.CSV_Reader;
import com.example.demo.Classroom;
import com.example.demo.Course;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class İlaydaTest {
    public static void main(String[] args) {
        CSV_Reader reader = new CSV_Reader();
        ClassroomDataAccessObject cdo = new ClassroomDataAccessObject();
        CourseDataAccessObject cdo2 = new CourseDataAccessObject();
        AttendenceDatabase ado = new AttendenceDatabase();
        Admin admin = new Admin();

        try {
            cdo.createTable();
            cdo.addClassroom(reader.readClassrooms());
            ArrayList<Classroom> classroomsList = cdo.getClassrooms();

            cdo2.createTable();
            cdo2.addCourse(reader.readCourses());
            ArrayList<Course> courseList = cdo2.getCourses();

            ado.createTables();
            ado.addStudentsFromCSV();

            ArrayList<Course> lecturerCourses = cdo2.getCourseWhereLecturerIs("Kaya Oğuz");
            ArrayList<Course> studentCourses = cdo2.getCoursesBasedOnStudent("VELİALİ DOĞA GÜNEŞ");

            // Her ders için uygun sınıfı atama
            for (Course course : courseList) {
                //System.out.println(course.getCourseID() + " " + course.getEnrolledStudentsList().size());
                System.out.println(course.getCourseID() + " " + ado.studentsOfSpecificCourse(course).size());
                course.assignClassroom(classroomsList);
            }

            for(Course courses : lecturerCourses) {
                System.out.println(courses.getCourseID());
            }
            System.out.println("");
            for(Course courses : studentCourses) {
                System.out.println(courses.getCourseID());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
