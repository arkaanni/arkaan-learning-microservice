package dev.arkaan.schoolapp.studentservice.db;

import dev.arkaan.schoolapp.studentservice.api.Student;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.List;

public interface StudentDao extends SqlObject {

    @SqlQuery("SELECT id, student_id, first_name, last_name, address, phone " +
            "FROM student WHERE id=?")
    @UseRowMapper(StudentMapper.class)
    Student getStudent(int id);
}
