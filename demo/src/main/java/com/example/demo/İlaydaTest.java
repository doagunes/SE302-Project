package com.example.demo;

import com.example.demo.CSV_Reader;
import com.example.demo.Classroom;
import com.example.demo.Course;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class İlaydaTest {
    public static void main(String[] args) {
        Admin admin = new Admin();

        ArrayList<Course> courseList = CourseDataAccessObject.getCourses();
        ArrayList<Classroom> classroomsList = ClassroomDataAccessObject.getClassrooms();
        for (Course course : courseList) {
            //System.out.println(course.getCourseID() + " " + course.getEnrolledStudentsList().size());
            System.out.println(course + " " + course.getCourseID() + " " + AttendenceDatabase.studentsOfSpecificCourse(course).size());
            course.assignClassroom(classroomsList);
        }
        System.out.println("*********");
        for(Course crs : courseList) {
            System.out.println(crs + " " +  crs.getCourseID() + " " + crs.getAssignedClassroom().getClassroomName());
        }

    }
}
