package com.john.bookmgmt.api.dto

import org.springframework.http.HttpStatus

/**
 * @author yoonho
 * @since 2022.10.26
 */
class BaseResponse(
    val message: String?,
    val status: HttpStatus,
    val data: Any?
) {
    constructor(): this(message = "Success", status = HttpStatus.OK, null)
    constructor(message: String?, status: HttpStatus): this(message = message, status = status, null)

    fun success(data: Any?): BaseResponse {
        return BaseResponse(message = "Success", status = HttpStatus.OK, data)
    }

    fun successNoContent(): BaseResponse {
        return BaseResponse(message = "Success", status = HttpStatus.OK, null)
    }
}