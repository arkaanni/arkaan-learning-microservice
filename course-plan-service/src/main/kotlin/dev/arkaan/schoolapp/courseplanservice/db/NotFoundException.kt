package dev.arkaan.schoolapp.courseplanservice.db

import jakarta.ws.rs.WebApplicationException
import org.eclipse.jetty.http.HttpStatus

class NotFoundException(message: String) : WebApplicationException(message, HttpStatus.NOT_FOUND_404)