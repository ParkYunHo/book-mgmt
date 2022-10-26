package com.john.bookmgmt.service

import com.john.bookmgmt.constants.CommCode
import com.john.bookmgmt.dto.BookDto
import com.john.bookmgmt.dto.GenreDto
import com.john.bookmgmt.entity.Book
import com.john.bookmgmt.entity.Genre
import com.john.bookmgmt.exception.GraphQLNotFoundException
import com.john.bookmgmt.repository.BookRepository
import com.john.bookmgmt.repository.GenreRepository
import graphql.GraphQLException
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

    fun findBook(bookId: String, type: CommCode.Request): BookDto = bookRepository.findById(bookId).orElseThrow {
        logger.error(" >>> [findBook] not found book data")
        this.throwExceptionByRequest(type, "도서 조회에 실패하였습니다.")
    }.toDto()

    fun findGenre(genreCode: String, type: CommCode.Request): GenreDto = genreRepository.findById(genreCode).orElseThrow {
        logger.error(" >>> [findGenre] not found genre data")
        this.throwExceptionByRequest(type, "장르 조회에 실패하였습니다.")
    }.toDto()

    fun deleteBook(bookId: String, type: CommCode.Request) {
        if(!bookRepository.existsById(bookId)){
            logger.error(" >>> [deleteBook] not found genre data")
            this.throwExceptionByRequest(type, "삭제대상 도서가 없습니다.")
        }
        bookRepository.deleteById(bookId)
    }

    fun deleteGenre(genreCode: String, type: CommCode.Request) {
        if(!genreRepository.existsById(genreCode)){
            logger.error(" >>> [deleteGenre] not found genre data")
            this.throwExceptionByRequest(type, "삭제대상 장르가 없습니다.")
        }
        genreRepository.deleteById(genreCode)
    }

    fun saveBook(input: BookDto, type: CommCode.Request): BookDto {
        if(input.bookId.isNullOrBlank()){
            this.throwExceptionByRequest(type, "도서 ID가 존재하지 않습니다.")
        }

        val genre = findGenre(input.genreCode, CommCode.Request.REST)
        return bookRepository.save(Book(
                    bookId = input.bookId,
                    genre = Genre(genreCode = genre.genreCode, genreMainCategory = genre.genreMainCategory, genreSubCategory = genre.genreSubCategory),
                    bookName = input.bookName
                )).toDto()
    }

    fun saveGenre(input: GenreDto, type: CommCode.Request): GenreDto {
        if(input.genreCode.isNullOrBlank()){
            this.throwExceptionByRequest(type, "장르 ID가 존재하지 않습니다.")
        }

        return genreRepository.save(Genre(
                    genreCode = input.genreCode,
                    genreMainCategory = input.genreMainCategory,
                    genreSubCategory = input.genreSubCategory
                )).toDto()
    }

    fun throwExceptionByRequest(type: CommCode.Request, message: String): Throwable{
        when(type) {
            CommCode.Request.REST -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, message)
            CommCode.Request.GRAPHQL -> throw GraphQLNotFoundException(message)
        }
    }
}