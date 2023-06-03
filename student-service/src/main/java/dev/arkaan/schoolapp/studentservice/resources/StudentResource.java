package dev.arkaan.schoolapp.studentservice.resources;

import dev.arkaan.schoolapp.studentservice.db.StudentDao;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/students")
public class StudentResource {

    private final StudentDao studentDao;

    public StudentResource(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> allStudentFirstNames() {
        return studentDao.getAllFirstName();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> allStudentLastNames() {
        return studentDao.getAllLastName();
    }
}
