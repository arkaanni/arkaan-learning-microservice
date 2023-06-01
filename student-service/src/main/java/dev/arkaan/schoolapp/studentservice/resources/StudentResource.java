package dev.arkaan.schoolapp.studentservice.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/students")
public class StudentResource {

    @GET
    public String allStudents() {
        return "allStudents";
    }
}
