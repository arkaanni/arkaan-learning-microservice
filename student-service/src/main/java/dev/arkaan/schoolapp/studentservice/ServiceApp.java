package dev.arkaan.schoolapp.studentservice;

import dev.arkaan.schoolapp.studentservice.resources.StudentResource;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;

public class ServiceApp extends Application<ServiceConfiguration> {

    @Override
    public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {
        super.initialize(bootstrap);

        setEnvironmentVariableSubstitutor(bootstrap);
    }

    private void setEnvironmentVariableSubstitutor(Bootstrap<ServiceConfiguration> bootstrap) {
        var environmentVariableSubstitutor = new EnvironmentVariableSubstitutor();
        var substitutingSourceProvider = new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(), environmentVariableSubstitutor);
        bootstrap.setConfigurationSourceProvider(substitutingSourceProvider);
    }

    @Override
    public void run(ServiceConfiguration serviceConfiguration, Environment environment) {
        new JdbiFactory().build(environment, serviceConfiguration.getDb(), "mysql8");
        environment.jersey().register(StudentResource.class);
    }

    public static void main(String[] args) throws Exception {
        new ServiceApp().run(args);
    }
}
