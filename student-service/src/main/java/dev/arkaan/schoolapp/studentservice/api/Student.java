package dev.arkaan.schoolapp.studentservice.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Student {
    private String studentId;
    private String firstName;
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

    public String getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

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
