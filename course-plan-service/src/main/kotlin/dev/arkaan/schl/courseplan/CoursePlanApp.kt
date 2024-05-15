package dev.arkaan.schl.courseplan

import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.typesafe.config.Config
import dev.arkaan.schl.courseplan.client.ClientExtension
import dev.arkaan.schl.courseplan.db.ExposedExtension
import dev.arkaan.schl.courseplan.service.CoursePlanService
import dev.arkaan.schl.courseplan.service.ServiceExtension
import io.jooby.*
import io.jooby.jackson.JacksonModule
import io.jooby.kt.runApp
import io.jooby.undertow.UndertowServer

fun configureServer(config: Config) : ServerOptions.() -> Unit = {
    with(config.getConfig("server")) {
        workerThreads = getInt("workerThreads")
        ioThreads = getInt("ioThreads")
        port = getInt("port")
    }
}

fun main(args: Array<String>) {
    runApp(args) {
        install(UndertowServer())
        install(ExposedExtension())
        install(ClientExtension())
        install(ServiceExtension())
        services.put(KotlinModule::class.java, KotlinModule.Builder().build())
        val jacksonModule = JacksonModule()
            .module(KotlinModule::class.java)

        encoder(jacksonModule)
        decoder(MediaType.json, jacksonModule)

        use(ReactiveSupport.concurrent())

        executionMode = ExecutionMode.EVENT_LOOP

        serverOptions(configurer = configureServer(environment.config))

        error(StatusCode.NOT_FOUND) { ctx, _, _ ->
            ctx.send("nothing here!")
        }

        val coursePlanService = require(CoursePlanService::class.java)
        coroutine {
            path("/courseplan") {
                with(coursePlanService) {
                    get("/") { getCoursePlan() }
                    post("/") { createCoursePlan(ctx) }
                }
            }
        }
    }
}