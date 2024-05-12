package dev.arkaan.schl.courseplan.db

class Configuration(
    var datasource: DatasourceConfiguration
)

class DatasourceConfiguration(
    var jdbcUrl: String,
    var driverClass: String,
    var username: String,
    var password: String
)