package dev.arkaan.schoolapp.studentservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.core.Configuration;
import io.dropwizard.db.DataSourceFactory;
import jakarta.validation.constraints.NotNull;

public class ServiceConfiguration extends Configuration {

    @NotNull
    private DataSourceFactory db;

    @JsonProperty
    public void setDb(DataSourceFactory db) {
        this.db = db;
    }

    @JsonProperty
    public DataSourceFactory getDb() {
        return db;
    }
}
