package dev.arkaan.schoolapp.subjectservice.controller

import dev.arkaan.schoolapp.subjectservice.api.response.SubjectResponse
import dev.arkaan.schoolapp.subjectservice.service.SubjectService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import jakarta.inject.Inject

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
}