package co.uk.simple.app.web

import co.uk.simple.app.model.ApiError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@RestController
@ControllerAdvice
class GlobalExceptionHandler {
    private companion object {
        private const val UNEXPECTED_ERROR_MESSAGE: String = "An internal error has occurred"
    }

    @ExceptionHandler
    fun handleThrowable(throwable: Throwable): ResponseEntity<ApiError> = ResponseEntity<ApiError>(
        ApiError(message = throwable.message ?: UNEXPECTED_ERROR_MESSAGE),
        HttpStatus.INTERNAL_SERVER_ERROR
    )
}