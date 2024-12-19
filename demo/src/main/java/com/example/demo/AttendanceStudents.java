package com.example.demo;

public class AttendanceStudents  {
    public int attendanceId;
    public int studentId;
    public String studentName;
    public String courseName;
    public int absenceCount;

    public AttendanceStudents(int attendanceId, int studentId, String studentName, String courseName, int absenceCount) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseName = courseName;
        this.absenceCount = absenceCount;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }
}
