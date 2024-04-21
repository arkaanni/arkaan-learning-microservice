package dev.arkaan.schoolapp.subjectservice.controller

import dev.arkaan.schoolapp.subjectservice.api.DuplicateException
import dev.arkaan.schoolapp.subjectservice.api.request.SubjectRequest
import dev.arkaan.schoolapp.subjectservice.api.response.SubjectResponse
import dev.arkaan.schoolapp.subjectservice.domain.Subject
import dev.arkaan.schoolapp.subjectservice.service.SubjectService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import jakarta.inject.Inject
import java.sql.SQLIntegrityConstraintViolationException

@Controller("/subject")
class SubjectController @Inject constructor(
    private val subjectService: SubjectService
) {

    @Get
    fun getAllSubjects(): HttpResponse<List<SubjectResponse>> {
        return HttpResponse.ok(subjectService.getAll())
    }

    @Get("/{code}")
    fun getByCode(
        @PathVariable code: String,
    ): HttpResponse<SubjectResponse> {
        val subjectResponse = subjectService.getByCode(code)
        subjectResponse?.let {
            return HttpResponse.ok(it)
        }
        return HttpResponse.notFound()
    }

    @Post
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun insert(
        @Body subject: SubjectRequest
    ): HttpResponse<String> {
        try {
            subjectService.insert(subject)
            return HttpResponse.ok()
        } catch (e: DuplicateException) {
            return HttpResponse.badRequest(e.message)
        }
    }
}