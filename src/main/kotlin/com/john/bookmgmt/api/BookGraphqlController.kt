package com.john.bookmgmt.api

import com.john.bookmgmt.constants.CommCode
import com.john.bookmgmt.dto.BookDto
import com.john.bookmgmt.dto.GenreDto
import com.john.bookmgmt.exception.GraphQLNotFoundException
import com.john.bookmgmt.service.BookService
import lombok.NonNull
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RestController

/**
 * @author yoonho
 * @since 2022.10.26
 */
@RestController
class BookGraphqlController(
    private val bookService: BookService
) {
    @QueryMapping
    fun findBook(@Argument bookId: String?): BookDto {
        if(bookId.isNullOrBlank()){
            throw GraphQLNotFoundException("도서 ID가 존재하지 않습니다.")
        }

        return bookService.findBook(bookId, CommCode.Request.GRAPHQL)
    }

    @QueryMapping
    fun findGenre(@Argument genreCode: String?): GenreDto {
        if(genreCode.isNullOrBlank()){
            throw GraphQLNotFoundException("장르 ID가 존재하지 않습니다.")
        }

        return bookService.findGenre(genreCode, CommCode.Request.GRAPHQL)
    }

    @MutationMapping
    fun saveBook(@Argument input: BookDto?): BookDto {
        if(input == null){
            throw GraphQLNotFoundException("저장할 도서 데이터가 존재하지 않습니다.")
        }

        return bookService.saveBook(input, CommCode.Request.GRAPHQL)
    }

    @MutationMapping
    fun saveGenre(@Argument input: GenreDto?): GenreDto {
        if(input == null){
            throw GraphQLNotFoundException("저장할 장르 데이터가 존재하지 않습니다.")
        }

        return bookService.saveGenre(input, CommCode.Request.GRAPHQL)
    }

    @MutationMapping
    fun deleteBook(@Argument bookId: String?): Boolean {
        if(bookId.isNullOrBlank()){
            throw GraphQLNotFoundException("도서 ID가 존재하지 않습니다.")
        }

        bookService.deleteBook(bookId, CommCode.Request.GRAPHQL)
        return true
    }

    @MutationMapping
    fun deleteGenre(@Argument genreCode: String?): Boolean {
        if(genreCode.isNullOrBlank()){
            throw GraphQLNotFoundException("장르 ID가 존재하지 않습니다.")
        }

        bookService.deleteGenre(genreCode, CommCode.Request.GRAPHQL)
        return true
    }
}