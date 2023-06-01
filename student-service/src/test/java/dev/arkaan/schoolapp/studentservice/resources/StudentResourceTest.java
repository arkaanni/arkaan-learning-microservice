package dev.arkaan.schoolapp.studentservice.resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentResourceTest {

    private StudentResource studentResource;

    @BeforeEach
    void setup() {
        studentResource = new StudentResource();
    }

    @Test
    void testStudentResource() {
        String s = studentResource.allStudents();
        assertEquals("allStudents", s);
    }
}