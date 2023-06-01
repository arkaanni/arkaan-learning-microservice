package dev.arkaan.schoolapp.studentservice.db;

import org.jdbi.v3.sqlobject.statement.SqlQuery;
import java.util.List;

public interface StudentDao {

    @SqlQuery("SELECT first_name FROM student;")
    List<String> getAllFirstName();
}
