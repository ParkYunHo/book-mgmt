package com.john.bookmgmt.dto

import java.io.Serializable
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2022.10.26
 */
data class BookDto(
    var bookId: String,
    var genreCode: String,
    var genreMainCategory: String?,
    var genreSubCategory: String?,
    var bookName: String,
    var updatedAt: LocalDateTime?,
    var createdAt: LocalDateTime?
) : Serializable