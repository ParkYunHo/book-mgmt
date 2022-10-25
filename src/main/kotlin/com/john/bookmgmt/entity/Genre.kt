package com.john.bookmgmt.entity

import com.john.bookmgmt.dto.GenreDto
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * @author yoonho
 * @since 2022.10.26
 */
@Entity
@Table(name = "genre")
class Genre (
    @Id
    @Column(name = "genre_code", nullable = false)
    var genreCode: String,
    @Column(name = "genre_main_category", nullable = false)
    var genreMainCategory: String,
    @Column(name = "genre_sub_category", nullable = false)
    var genreSubCategory: String
) {
    fun toDto() = GenreDto(
        genreCode = this.genreCode,
        genreMainCategory = this.genreMainCategory,
        genreSubCategory = this.genreSubCategory
    )
}