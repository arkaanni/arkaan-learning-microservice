package dev.arkaan.schoolapp.studentservice;

import dev.arkaan.schoolapp.studentservice.resources.StudentResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;

public class ServiceApp extends Application<ServiceConfiguration> {

    @Override
    public void run(ServiceConfiguration serviceConfiguration, Environment environment) throws Exception {
        environment.jersey().register(StudentResource.class);
    }

    public static void main(String[] args) throws Exception {
        new ServiceApp().run(args);
    }
}
