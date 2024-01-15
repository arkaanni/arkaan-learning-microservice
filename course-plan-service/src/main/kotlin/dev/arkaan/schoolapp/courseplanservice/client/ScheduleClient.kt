package dev.arkaan.schoolapp.courseplanservice.client

import com.google.inject.ImplementedBy

@ImplementedBy(ScheduleHttpClient::class)
interface ScheduleClient {
    suspend fun checkScheduleExist(id: String)
}