package com.john.bookmgmt.dto

import java.io.Serializable
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2022.10.26
 */
data class GenreDto(
    var genreCode: String,
    var genreMainCategory: String,
    var genreSubCategory: String,
) : Serializable