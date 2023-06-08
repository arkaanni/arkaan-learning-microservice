package dev.arkaan.schoolapp.studentservice.resources;

import dev.arkaan.schoolapp.studentservice.db.StudentDao;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/students")
public class StudentResource {

    private final StudentDao studentDao;

    public StudentResource(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getStudentById(@PathParam("id") String id) {
        return Response.ok(studentDao.getStudent(id)).build();
    }
}
