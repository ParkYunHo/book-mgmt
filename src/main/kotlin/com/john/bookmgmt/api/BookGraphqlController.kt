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

    /**
     * 도서정보 조회 (Graphql)
     *
     * @param bookId [String]
     * @return response [BookDto]
     * @author yoonho
     * @since 2022.11.09
     */
    @QueryMapping
    fun findBook(@Argument bookId: String?): BookDto {
        if(bookId.isNullOrBlank()){
            throw GraphQLNotFoundException("도서 ID가 존재하지 않습니다.")
        }

        return bookService.findBook(bookId, CommCode.Request.GRAPHQL)
    }

    /**
     * 장르정보 조회 (Graphql)
     *
     * @param genreCode [String]
     * @return response [GenreDto]
     * @author yoonho
     * @since 2022.11.09
     */
    @QueryMapping
    fun findGenre(@Argument genreCode: String?): GenreDto {
        if(genreCode.isNullOrBlank()){
            throw GraphQLNotFoundException("장르 ID가 존재하지 않습니다.")
        }

        return bookService.findGenre(genreCode, CommCode.Request.GRAPHQL)
    }

    /**
     * 도서정보 저장 (Graphql)
     *
     * @param input [BookDto]
     * @return response [BookDto]
     * @author yoonho
     * @since 2022.11.09
     */
    @MutationMapping
    fun saveBook(@Argument input: BookDto?): BookDto {
        if(input == null){
            throw GraphQLNotFoundException("저장할 도서 데이터가 존재하지 않습니다.")
        }

        return bookService.saveBook(input, CommCode.Request.GRAPHQL)
    }

    /**
     * 장르정보 저장 (Graphql)
     *
     * @param input [GenreDto]
     * @return response [GenreDto]
     * @author yoonho
     * @since 2022.11.09
     */
    @MutationMapping
    fun saveGenre(@Argument input: GenreDto?): GenreDto {
        if(input == null){
            throw GraphQLNotFoundException("저장할 장르 데이터가 존재하지 않습니다.")
        }

        return bookService.saveGenre(input, CommCode.Request.GRAPHQL)
    }

    /**
     * 도서정보 삭제 (Graphql)
     *
     * @param bookId [String]
     * @return response [Boolean]
     * @author yoonho
     * @since 2022.11.09
     */
    @MutationMapping
    fun deleteBook(@Argument bookId: String?): Boolean {
        if(bookId.isNullOrBlank()){
            throw GraphQLNotFoundException("도서 ID가 존재하지 않습니다.")
        }

        bookService.deleteBook(bookId, CommCode.Request.GRAPHQL)
        return true
    }

    /**
     * 장르정보 삭제 (Graphql)
     *
     * @param genreCode [String]
     * @return response [Boolean]
     * @author yoonho
     * @since 2022.11.09
     */
    @MutationMapping
    fun deleteGenre(@Argument genreCode: String?): Boolean {
        if(genreCode.isNullOrBlank()){
            throw GraphQLNotFoundException("장르 ID가 존재하지 않습니다.")
        }

        bookService.deleteGenre(genreCode, CommCode.Request.GRAPHQL)
        return true
    }
}