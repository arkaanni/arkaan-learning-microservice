package dev.arkaan.schoolapp.studentservice.db;

import dev.arkaan.schoolapp.studentservice.api.Student;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

public interface StudentDao extends SqlObject {

    @SqlQuery("SELECT id, student_id, first_name, last_name, address, phone " +
            "FROM student WHERE student_id=?")
    @UseRowMapper(StudentMapper.class)
    Student getStudent(String id);
}
