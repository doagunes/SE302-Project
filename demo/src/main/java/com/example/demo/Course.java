package com.example.demo;

import com.almasb.fxgl.core.collection.Array;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

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


    public Course(String courseID, String timeToStart,int duration, String lecturerName, ArrayList<String> studentNames) {
        this.courseID = courseID;
        this.duration = duration;
        this.lecturerName = lecturerName;
        this.studentNames = studentNames;
        this.timeToStart = timeToStart;

        String[] timeParts = timeToStart.split(" ");
        this.courseDay = timeParts[0];
        this.startTime = LocalTime.parse(timeParts[1], DateTimeFormatter.ofPattern("H:mm"));
        this.endTime = this.getEndTime(startTime);

        //TODO: Lecturer ve Students login yaptığında oluşturalım dedik. Login Interface geldiğinde burası değiştirilebilir.

        this.lecturer = createLecturer(lecturerName);
        this.lecturer.getCourses().add(this);

        this.enrolledStudentsList = createStudents(studentNames);

    }

    public void assignClassroom(ArrayList<Classroom> classrooms) {
        if (classrooms == null || classrooms.isEmpty()) {
            System.out.println("No classrooms available for assignment.");
            return;
        }

        int studentCount = this.getStudentNames().size();
        String courseDay = this.courseDay;
        LocalTime startTime = this.startTime;
        LocalTime endTime = this.endTime;

        classrooms.sort(Comparator.comparingInt(Classroom::getCapacity).reversed());

        // En büyük kapasiteye sahip olan sınıflar arasından uygun olanı bul
        for (Classroom classroom : classrooms) {
            if (classroom.getCapacity() >= studentCount && classroom.isAvailable(courseDay, startTime, endTime)) {
                this.assignedClassroom = classroom;
                classroom.getCourses().add(this);
                System.out.println(this.getCourseID() + ": " + classroom.getClassroomName() + " " + classroom.getCapacity()); //test için
                System.out.println(this.getCourseDay() + "-" + this.getStartTime() + "-" + this.getEndTime(this.getStartTime())); //test için
                return;
            }
        }

        System.out.println("No suitable classroom found for course: " + this.getCourseID());
    }

    public Lecturer createLecturer(String lecturerName) {
        return Lecturer.findLecturerByName(lecturerName);
    }

    public ArrayList<Student> createStudents(ArrayList<String> studentNames) {
        ArrayList<Student> students = new ArrayList<>();
        for (String studentName : studentNames) {
            Student student = Student.findStudentByName(studentName);
            students.add(student);
            student.getCourses().add(this);
        }
        return students;
    }

    public LocalTime getEndTime(LocalTime startTime) {
        int totalDuration = duration * 45 + (duration - 1) * 10;
        endTime = startTime.plusMinutes(totalDuration);
        return endTime;
    }

    public void toChangeClassroom(ArrayList<Classroom> classrooms) {
        for(Classroom classroom : classrooms) {
            if(classroom.isAvailable(getTimeToStart(),getStartTime(),getEndTime())){
                if(classroom.getCapacity() >= getEnrolledStudentsList().size()) {
                    Classroom updatelenecekClassroom = getAssignedClassroom();
                    setAssignedClassroom(classroom);
                }
            }

        }
    }

    @Override
    public void add() {}

    @Override
    public void remove() {}

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
