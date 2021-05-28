package com.zup.exception

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.validation.exceptions.ConstraintExceptionHandler
import java.lang.RuntimeException
import javax.validation.ConstraintViolationException
import javax.inject.Singleton


@Produces
@Singleton
@Requires(classes = [RuntimeException::class, ExceptionHandler::class])
class GlobalExceptionHandler :
    ExceptionHandler<RuntimeException, HttpResponse<*>?> {

    override fun handle(request: HttpRequest<*>?, exception: RuntimeException?): HttpResponse<*>? {
        return when(exception){
            is StatusRuntimeException -> grpcError(exception)
            else -> HttpResponse.serverError<Any>()
        }
    }

    private fun grpcError(e: StatusRuntimeException): HttpResponse<Any> {
        return when (e.status.code) {
            Status.PERMISSION_DENIED.code -> HttpResponse.status<Any>(HttpStatus.UNAUTHORIZED)
                .body(ErrorDto(e.message, HttpStatus.UNAUTHORIZED.code))
            Status.INVALID_ARGUMENT.code -> HttpResponse.badRequest(
                ErrorDto(e.message, HttpStatus.BAD_REQUEST.code)
            )
            Status.ALREADY_EXISTS.code -> HttpResponse.unprocessableEntity<Any>()
                .body(ErrorDto(e.message, HttpStatus.UNPROCESSABLE_ENTITY.code))
            Status.NOT_FOUND.code -> HttpResponse.notFound(
                ErrorDto(e.message, HttpStatus.NOT_FOUND.code)
            )
            Status.UNKNOWN.code -> HttpResponse.serverError()
            else -> HttpResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
        }
    }
}



@Singleton
@Produces
@Replaces(ConstraintExceptionHandler::class)
@Requires(classes = [ConstraintViolationException::class, ExceptionHandler::class])
class ConExceptionHandler :
    ExceptionHandler<ConstraintViolationException, HttpResponse<*>?> {

    override fun handle(request: HttpRequest<*>?, exception: ConstraintViolationException?): HttpResponse<*>? {
        return HttpResponse.ok<Any>()
    }
}


