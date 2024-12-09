package com.example.demo;

import com.almasb.fxgl.core.collection.Array;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Course implements IGeneric {
    private String courseID;
    private String timeToStart;
    private int duration;
    private String lecturerName;
    private ArrayList<String> studentNames;
    private String courseDay;
    private LocalTime startTime;
    private LocalTime endTime;
    private Lecturer lecturer;
    private Classroom assignedClassroom;
    private ArrayList<Student> enrolledStudentsList;
    private ArrayList<Attendance> attendanceRecordList;

    //TODO: Maalesefki parametrelerde olan lecturer ve student objelerini String ile değiştirmem gerekti çünkü
    //TODO: csv den sadece öğrencinin ve öğretmenin adını çekebiliyoruz ID'lerini bulabileceğimiz bir data kaynagı yok tahminimce login ekranında
    //TODO: Öğrenci ya da Öğretmen register olurken bu ID datası sistemimize gelicek ve o zaman Lecturer ve Student Objeleri oluşturabilicez
    //TODO: Şu anlık bu sebeplerden dolayı referansları değiştridim eğer bu sorun aklınıza çözüm gelirse lütfen değiştirin.

    public Course(String courseID, String timeToStart,int duration, String lecturerName, ArrayList<String> studentNames) {
        this.courseID = courseID;
        this.duration = duration;
        this.lecturerName = lecturerName;
        this.studentNames = studentNames;
        //this.timeToStart = timeToStart;

        /*
        day ve startTime timeToStart Attributendan arada " " (boşluk) ile pars edilerek initialize edilicek
        çünkü csv'deki parametre gün ve başlama saatini ";" ile ayırmamış.
         */
        String[] timeParts = timeToStart.split(" ");
        this.courseDay = timeParts[0];
        this.startTime = LocalTime.parse(timeParts[1], DateTimeFormatter.ofPattern("H:mm"));
        this.endTime = this.getEndTime(startTime);

        //TODO Person için gerekli olan bütün attributelar sağlandıtan sonra Student ve Lecturer Objeleri oluşturulup Course Objesinin gerekli attributeları ile initialize edilmeli.

    }

    public LocalTime getEndTime(LocalTime startTime) {
        int totalDuration = duration * 45 + (duration - 1) * 10;
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

    public String getCourseDay() {
        return courseDay;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getTimeToStart() {
        return timeToStart;
    }

    public void setTimeToStart(String timeToStart) {
        this.timeToStart = timeToStart;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public ArrayList<String> getStudentNames() {
        return studentNames;
    }

    public void setStudentNames(ArrayList<String> studentNames) {
        this.studentNames = studentNames;
    }

    public void setCourseDay(String courseDay) {
        this.courseDay = courseDay;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Classroom getAssignedClassroom() {
        return assignedClassroom;
    }

    public void setAssignedClassroom(Classroom assignedClassroom) {
        this.assignedClassroom = assignedClassroom;
    }

    public ArrayList<Student> getEnrolledStudentsList() {
        return enrolledStudentsList;
    }

    public void setEnrolledStudentsList(ArrayList<Student> enrolledStudentsList) {
        this.enrolledStudentsList = enrolledStudentsList;
    }

    public ArrayList<Attendance> getAttendanceRecordList() {
        return attendanceRecordList;
    }

    public void setAttendanceRecordList(ArrayList<Attendance> attendanceRecordList) {
        this.attendanceRecordList = attendanceRecordList;
    }
}
