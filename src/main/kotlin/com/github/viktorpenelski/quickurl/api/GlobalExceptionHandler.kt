package com.github.viktorpenelski.quickurl.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime


@ControllerAdvice
class GlobalControllerExceptionHandler {

    data class ErrorsDetails(
            val msg: String,
            val time: LocalDateTime = LocalDateTime.now()
    )

    @ExceptionHandler(value = [(Throwable::class)])
    fun handleUserAlreadyExists(ex: Exception, request: WebRequest): ResponseEntity<ErrorsDetails> {

        return when (ex) {
            is HttpMessageNotReadableException -> ResponseEntity(
                    ErrorsDetails(ex.mostSpecificCause.message ?: ""),
                    HttpStatus.BAD_REQUEST)
            is BadRequestException -> ResponseEntity(ErrorsDetails(ex.message ?: ""), HttpStatus.BAD_REQUEST)
            else -> ResponseEntity(ErrorsDetails("Big sorry! 💤"), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

}