package dev.arkaan.schl.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class UriConfiguration {

    @Value("${student.service}")
    private String studentService;

    @Value("${room.service}")
    private String roomService;

    @Value("${courseplan.service}")
    private String coursePlanService;

    @Value("${subject.service}")
    private String subjectService;

    public String getStudentService() {
        return studentService;
    }

    public String getRoomService() {
        return roomService;
    }

    public String getCoursePlanService() {
        return coursePlanService;
    }

    public String getSubjectService() {
        return subjectService;
    }
}
