package dev.arkaan.schoolapp.courseplanservice.client

import com.google.inject.ImplementedBy

@ImplementedBy(StudentHttpClient::class)
interface StudentClient {
    suspend fun checkStudentExist(id: String): Boolean
}