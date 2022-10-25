package com.john.bookmgmt.exception

import com.john.bookmgmt.api.dto.BaseResponse
import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

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
}