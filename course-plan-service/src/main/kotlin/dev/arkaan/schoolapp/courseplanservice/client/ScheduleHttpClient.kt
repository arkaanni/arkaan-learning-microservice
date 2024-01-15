package dev.arkaan.schoolapp.courseplanservice.client

import dev.arkaan.schoolapp.courseplanservice.db.NotFoundException
import jakarta.inject.Inject
import jakarta.inject.Named
import jakarta.ws.rs.client.WebTarget
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.jetty.http.HttpStatus

class ScheduleHttpClient @Inject constructor(
    @Named("ScheduleClient") private val studentApi: WebTarget
) : ScheduleClient {

    private val getSchedule: (id: String) -> Response = {
        studentApi.path("/schedule/$it")
            .request(MediaType.APPLICATION_JSON_TYPE)
            .get()
    }

    private val getRecurringSchedule: (id: String) -> Response = {
        studentApi.path("/schedule/recurring/$it")
            .request(MediaType.APPLICATION_JSON_TYPE)
            .get()
    }

    override suspend fun checkScheduleExist(id: String) {
        if (getSchedule(id).status == HttpStatus.NOT_FOUND_404) {
            if (getRecurringSchedule(id).status == HttpStatus.NOT_FOUND_404) {
                throw NotFoundException("Schedule does not exists.")
            }
        }
    }
}