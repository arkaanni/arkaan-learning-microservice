package dev.arkaan.schoolapp.studentservice.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Student {
    @JsonProperty("student_id")
    private String studentId;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String address;
    private String phone;
    private byte semester;

    public Student() {}

    public Student(String studentId, String firstName, String lastName, String address, String phone, byte semester) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.semester = semester;
    }

    @JsonProperty("student_id")
    public String getStudentId() {
        return studentId;
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public byte getSemester() {
        return semester;
    }
}
