package dev.arkaan.schoolapp.courseplanservice.client

import com.google.inject.ImplementedBy

@ImplementedBy(SubjectHttpClient::class)
interface SubjectClient {
    suspend fun checkSubjectExist(code: String)
}