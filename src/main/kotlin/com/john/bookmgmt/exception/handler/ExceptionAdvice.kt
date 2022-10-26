package com.john.bookmgmt.exception.handler

import com.john.bookmgmt.api.dto.BaseResponse
import com.john.bookmgmt.exception.ParameterNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException

/**
 * @author yoonho
 * @since 2022.10.26
 */
@RestControllerAdvice
class ExceptionAdvice {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(EmptyResultDataAccessException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun emptyResultDataAccessException(e: EmptyResultDataAccessException): BaseResponse {
        logger.error(e.message, e)
        return BaseResponse(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(BindException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun bindException(e: BindException): BaseResponse {
        logger.error(e.message, e)
        return BaseResponse(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ResponseStatusException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun responseStatusException(e: ResponseStatusException): BaseResponse {
        logger.error(e.message, e)
        return BaseResponse(e.reason, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun missingServletRequestParameterException(e: MissingServletRequestParameterException): BaseResponse {
        logger.error(e.message, e)
        return BaseResponse("Required parameter is empty", HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ParameterNotFoundException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun parameterNotFoundException(e: ParameterNotFoundException): BaseResponse {
        logger.error(e.message, e)

        var msg = "Required parameter is empty"
        if(!e.message.isNullOrBlank()){
            msg = e.message
        }

        return BaseResponse(msg, HttpStatus.BAD_REQUEST)
    }
}