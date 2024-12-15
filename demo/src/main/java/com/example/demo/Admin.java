package com.example.demo;

import com.example.demo.Classroom;
import com.example.demo.Course;
import com.example.demo.Student;

import java.util.ArrayList;
import java.util.List;

public class Admin {
    private CourseDataAccessObject courseDAO;
    private ClassroomDataAccessObject classroomDAO;

    public Admin() {
        courseDAO = new CourseDataAccessObject();
        classroomDAO = new ClassroomDataAccessObject();
    }

    //TODO: BU METODLAR DAHA TEST EDİLMEDİ.

    //TODO: PARAMETREDEKİ COURSE'UN CLASSROOMU VE STUDENT'IN COURSELARI
    public void addStudentToCourse(Course course, Student student) {
        Classroom cls = course.getAssignedClassroom();
        //TODO 1öğrenci yeni bir kursa eklencekken zaman çizelgesi o ders için uygun mu? kontrol edilcek
        //TODO Yeni bir if eklenicek

        if(!student.getCourses().contains(course)) {
            if(cls.getCapacity() > course.getEnrolledStudentsList().size()) {
                if(student.isAvaiable(course)){
                    student.getCourses().add(course);
                    course.getEnrolledStudentsList().add(student);
                }

            } else {
                System.out.println("There is no space in the classroom.");
            }
        } else {
            System.out.println("Transfer failed: The student is already enrolled in this course");
        }
        //TODO öğrenci yeni kursa geçince sql update!
    }

    public void removeStudentFromCourse(Course course, Student student) {
        if(student.getCourses().contains(course)) {
            student.getCourses().remove(course);
            course.getEnrolledStudentsList().remove(student);
        } else {
            System.out.println("Transfer failed: The student is not enrolled in this course.");
        }
        //TODO SQL UPDATE
    }

    public void transferStudentToAnotherCourse(Course enrolledCourse, Course transferCourse, Student student) {
        //TODO  öğrenci yeni bir kursa eklencekken zaman çizelgesi o ders için uygun mu? kontrol edilcek
        if(student.getCourses().contains(enrolledCourse) && !student.getCourses().contains(transferCourse)) {
            if(transferCourse.getAssignedClassroom().getCapacity() > transferCourse.getEnrolledStudentsList().size()) {
                student.getCourses().add(transferCourse);
                student.getCourses().remove(enrolledCourse);
                transferCourse.getEnrolledStudentsList().remove(student);
                enrolledCourse.getEnrolledStudentsList().add(student);
            } else {
                System.out.println("There is no space in the classroom.");
            }
        } else {
            System.out.println("Transfer failed: Either the student is not enrolled in the course or already transferred.");
        }
        //TODO SQL Update
    }

    //TODO: COURSENUN CLASSROOMU, COURSEUN STUDENT LİSTİ, CLASSROOMUN CAPACİTYSİ,
    public void changeClassroom(Course course, Classroom newClassroom) {
        Classroom currentClassroom = course.getAssignedClassroom();
        int studentCount = course.getEnrolledStudentsList().size();
        // Check if the new classroom is available and has enough capacity
        if (!newClassroom.isAvailable(course.getCourseDay(), course.getStartTime(), course.getEndTime())) {
            System.out.println("The new classroom is not available during the course time.");
            return;
        }
        if (newClassroom.getCapacity() < studentCount) {
            System.out.println("The new classroom doesn't have enough capacity for the course.");
            return;
        }

        if (currentClassroom != null) {
            currentClassroom.getCourses().remove(course);
        }

        course.setAssignedClassroom(newClassroom);
        newClassroom.getCourses().add(course);
        System.out.println("Classroom changed to: " + newClassroom.getClassroomName());
        //TODO SQL UPDATE
    }


    public void markAttendanceForStudent(Student student, Course course, boolean isPresent) {
        // Öğrencinin o derse ait devamsızlık kaydını işaretle
        student.markAttendanceForCourse(course, isPresent);

        if (!isPresent) {
            System.out.println("Student " + student.getName() + " has been marked absent for course " + course.getCourseID());
        } else {
            System.out.println("Student " + student.getName() + " is present in course " + course.getCourseID());
        }
        //TODO sql update
    }

    public void viewStudentAbsenteeism(Student student, Course course) {
        // Öğrencinin o dersteki devamsızlık sayısını al
        int absenteeismCount = student.getAbsenceCountForCourse(course);
        System.out.println("Student " + student.getName() + " has " + absenteeismCount + " absences in course " + course.getCourseID());
    }
    //TODO SQL getAttendence

    // Öğrencinin tüm derslerdeki devamsızlıklarını görüntüleme
    public void viewAllAbsenteeismForStudent(Student student) {
        System.out.println("Absenteeism details for student: " + student.getName());
        for (Course course : student.getCourses()) {
            int absenteeismCount = student.getAbsenceCountForCourse(course);
            System.out.println("In course " + course.getCourseID() + ": " + absenteeismCount + " absences.");
        }
        //TODO SQL spesifik öğrenci için devamsılık göstercek
    }
}