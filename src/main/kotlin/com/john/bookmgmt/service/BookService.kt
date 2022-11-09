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

    /**
     * 도서정보 조회
     *
     * @param bookId [String]
     * @param type [CommCode.Request]
     * @return response [BookDto]
     * @throws ResponseStatusException
     * @throws GraphQLNotFoundException
     * @author yoonho
     * @since 2022.11.09
     */
    fun findBook(bookId: String, type: CommCode.Request): BookDto = bookRepository.findById(bookId).orElseThrow {
        logger.error(" >>> [findBook] not found book data")
        this.throwExceptionByRequest(type, "도서 조회에 실패하였습니다.")
    }.toDto()

    /**
     * 장르정보 조회
     *
     * @param genreCode [String]
     * @param type [CommCode.Request]
     * @return response [GenreDto]
     * @throws ResponseStatusException
     * @throws GraphQLNotFoundException
     * @author yoonho
     * @since 2022.11.09
     */
    fun findGenre(genreCode: String, type: CommCode.Request): GenreDto = genreRepository.findById(genreCode).orElseThrow {
        logger.error(" >>> [findGenre] not found genre data")
        this.throwExceptionByRequest(type, "장르 조회에 실패하였습니다.")
    }.toDto()

    /**
     * 도서정보 삭제
     *
     * @param bookId [String]
     * @param type [CommCode.Request]
     * @throws ResponseStatusException
     * @throws GraphQLNotFoundException
     * @author yoonho
     * @since 2022.11.09
     */
    fun deleteBook(bookId: String, type: CommCode.Request) {
        if(!bookRepository.existsById(bookId)){
            logger.error(" >>> [deleteBook] not found genre data")
            this.throwExceptionByRequest(type, "삭제대상 도서가 없습니다.")
        }
        bookRepository.deleteById(bookId)
    }

    /**
     * 장르정보 삭제
     *
     * @param genreCode [String]
     * @param type [CommCode.Request]
     * @throws ResponseStatusException
     * @throws GraphQLNotFoundException
     * @author yoonho
     * @since 2022.11.09
     */
    fun deleteGenre(genreCode: String, type: CommCode.Request) {
        if(!genreRepository.existsById(genreCode)){
            logger.error(" >>> [deleteGenre] not found genre data")
            this.throwExceptionByRequest(type, "삭제대상 장르가 없습니다.")
        }
        genreRepository.deleteById(genreCode)
    }

    /**
     * 도서정보 저장
     *
     * @param input [BookDto]
     * @param type [CommCode.Request]
     * @throws ResponseStatusException
     * @throws GraphQLNotFoundException
     * @return response [BookDto]
     * @author yoonho
     * @since 2022.11.09
     */
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

    /**
     * 장르정보 저장
     *
     * @param input [GenreDto]
     * @param type [CommCode.Request]
     * @throws ResponseStatusException
     * @throws GraphQLNotFoundException
     * @return response [GenreDto]
     * @author yoonho
     * @since 2022.11.09
     */
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

    /**
     * 호출타입에 따른 Exception throw
     *
     * @param type [CommCode.Request]
     * @param message [String]
     * @throws ResponseStatusException
     * @throws GraphQLNotFoundException
     * @author yoonho
     * @since 2022.11.09
     */
    fun throwExceptionByRequest(type: CommCode.Request, message: String): Throwable{
        when(type) {
            CommCode.Request.REST -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, message)
            CommCode.Request.GRAPHQL -> throw GraphQLNotFoundException(message)
        }
    }
}