package dev.arkaan.schoolapp.studentservice.resources;

import dev.arkaan.schoolapp.studentservice.db.StudentDao;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

@Path("/students")
public class StudentResource {

    private final StudentDao studentDao;

    public StudentResource(Jdbi jdbi) {
        this.studentDao = jdbi.onDemand(StudentDao.class);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> allStudentFirstNames() {
        return studentDao.getAllFirstName();
    }
}
