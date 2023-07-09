package dev.arkaan.schoolapp.studentservice.db;

import dev.arkaan.schoolapp.studentservice.api.Student;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

public interface StudentDao extends SqlObject {

    @SqlQuery("SELECT student_id, first_name, last_name, address, phone, semester " +
            "FROM student WHERE student_id=?")
    @UseRowMapper(StudentMapper.class)
    Student getStudent(String id);

    @SqlUpdate("INSERT INTO student (student_id, first_name, last_name, address, phone, semester) " +
            "VALUES(:studentId, :firstName, :lastName, :address, :phone, :semester)")
    void addOne(@BindBean Student student);
}
