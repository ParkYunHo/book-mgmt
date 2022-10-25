package com.john.bookmgmt.service

import com.john.bookmgmt.dto.BookDto
import com.john.bookmgmt.dto.GenreDto
import com.john.bookmgmt.entity.Book
import com.john.bookmgmt.entity.Genre
import com.john.bookmgmt.repository.BookRepository
import com.john.bookmgmt.repository.GenreRepository
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

/**
 * @author yoonho
 * @since 2022.10.26
 */
@Service
class BookService(
    private val bookRepository: BookRepository,
    private val genreRepository: GenreRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun findBook(bookId: String): BookDto = bookRepository.findById(bookId).orElseThrow {
        logger.error(" >>> [findBook] not found book data")
        throw ResponseStatusException(HttpStatus.BAD_REQUEST,"도서 조회에 실패하였습니다.")
    }.toDto()

    fun findGenre(genreCode: String): GenreDto = genreRepository.findById(genreCode).orElseThrow {
        logger.error(" >>> [findGenre] not found genre data")
        throw ResponseStatusException(HttpStatus.BAD_REQUEST,"장르 조회에 실패하였습니다.")
    }.toDto()

    fun deleteBook(bookId: String) = bookRepository.deleteById(bookId)

    fun deleteGenre(genreCode: String) = genreRepository.deleteById(genreCode)

    fun saveBook(input: BookDto): BookDto {
        val genre = findGenre(input.genreCode)
        return bookRepository.save(
            Book(
                bookId = input.bookId,
                genre = Genre(genreCode = genre.genreCode, genreMainCategory = genre.genreMainCategory, genreSubCategory = genre.genreSubCategory),
                bookName = input.bookName
            )
        ).toDto()
    }

    fun saveGenre(input: GenreDto): GenreDto {
        return genreRepository.save(
            Genre(
                genreCode = input.genreCode,
                genreMainCategory = input.genreMainCategory,
                genreSubCategory = input.genreSubCategory
            )
        ).toDto()
    }
}