package dev.arkaan.schoolapp.courseplanservice

import io.dropwizard.core.Application
import io.dropwizard.core.setup.Environment

class CoursePlanServiceApp : Application<CoursePlanServiceConfiguration>() {
    override fun run(configuration: CoursePlanServiceConfiguration, environment: Environment) {

    }
}

fun main(args: Array<String>) {
    CoursePlanServiceApp().run(*args)
}