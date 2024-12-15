package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {
    private CSV_Reader csvReader;
    private ArrayList<Course> courses;
    private ArrayList<Classroom> classrooms;

    @BeforeEach
    public void setUp() throws IOException {
        csvReader = new CSV_Reader();
        courses = csvReader.readCourses();
        classrooms = csvReader.readClassrooms();
    }

    @Test
    public void testAssignClassrooms() {
        // Sort classrooms by capacity (desc) and courses by student count (desc)
        courses.sort((c1, c2) -> Integer.compare(c2.getStudents().size(), c1.getStudents().size()));
        classrooms.sort((cl1, cl2) -> Integer.compare(cl2.getCapacity(), cl1.getCapacity()));

        // Assign classrooms to courses and validate the assignments
        for (Course course : courses) {
            // Call assignClassroom (doesn't return anything)
            course.assignClassroom(classrooms);

            // Check that the course has an assigned classroom
            Classroom assignedClassroom = course.getAssignedClassroom();
            assertNotNull(assignedClassroom, "Classroom should be assigned.");

            // Classroom capacity should be greater than or equal to the student count
            assertTrue(assignedClassroom.getCapacity() >= course.getStudents().size(),
                    "Classroom capacity should be sufficient for the course.");

            // Check if the assigned classroom is available
            boolean isAvailable = assignedClassroom.isAvailable(course.getCourseDay(), course.getStartTime(), course.getEndTime(course.getStartTime()));
            assertTrue(isAvailable, "Classroom should be available at the course time.");
        }
    }
}
