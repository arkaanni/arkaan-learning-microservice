package dev.arkaan.schoolapp.studentservice.resources;

import dev.arkaan.schoolapp.studentservice.db.StudentDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentResourceTest {

    @InjectMocks
    private StudentResource studentResource;

    @Mock
    private StudentDao studentDao;

    @BeforeEach
    void setup() {
        studentResource = new StudentResource(studentDao);
    }

    @Test
    void testStudentResource() {
        var allFirstName = List.of("John");
        when(studentDao.getAllFirstName()).thenReturn(allFirstName);
        var s = studentResource.allStudentFirstNames();
        assertEquals(s.get(0), allFirstName.get(0));
    }

    @Test
    void testGetAllLastName() {
        var lastNameList = List.of("Titor");
        when(studentDao.getAllLastName()).thenReturn(lastNameList);
        var result = studentResource.allStudentLastNames();
        assertEquals(result.get(0), lastNameList.get(0));
    }
}