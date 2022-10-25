package com.john.bookmgmt.entity

import com.john.bookmgmt.dto.BookDto
import java.time.LocalDateTime
import javax.persistence.*

/**
 * @author yoonho
 * @since 2022.10.26
 */
@Entity
@Table(name = "book")
class Book (
    @Id
    @Column(name = "book_id", nullable = false)
    var bookId: String,

    @ManyToOne
    @JoinColumn(name = "genre_code", nullable = false)
    var genre: Genre,

    @Column(name = "book_name", nullable = true)
    var bookName: String,
    @Column(name = "updated_at", nullable = true)
    var updatedAt: LocalDateTime? = null,
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
) {
    fun toDto() = BookDto(
        bookId = this.bookId,
        genreCode = this.genre.genreCode,
        genreMainCategory = this.genre.genreMainCategory,
        genreSubCategory = this.genre.genreSubCategory,
        bookName = this.bookName,
        updatedAt = this.updatedAt,
        createdAt = this.createdAt
    )
}