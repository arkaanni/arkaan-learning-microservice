package dev.arkaan.schl.studentservice.db;

import dev.arkaan.schl.studentservice.api.Student;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {
    @Override
    public Student map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Student(
                rs.getString(  1), rs.getString(2),
                rs.getString(3), rs.getString(4),
                rs.getString(5), rs.getByte(6)
        );
    }
}
