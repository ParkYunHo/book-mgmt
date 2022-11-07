package com.john.bookmgmt.api

import com.john.bookmgmt.api.dto.BaseResponse
import com.john.bookmgmt.constants.CommCode
import com.john.bookmgmt.dto.BookDto
import com.john.bookmgmt.dto.GenreDto
import com.john.bookmgmt.exception.ParameterNotFoundException
import com.john.bookmgmt.service.BookService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

/**
 * @author yoonho
 * @since 2022.10.26
 */
@Tag(name = "도서관리 시스템", description = "도서관리 시스템 API")
@RestController
class BookController(
    private val bookService: BookService
) {

    @Operation(summary = "도서정보 조회")
    @GetMapping("/api/{type}")
    fun find(@PathVariable("type") type: String, @RequestParam id: String): BaseResponse {
        if(id.isNullOrBlank()){
            throw ParameterNotFoundException("Required Parameter is empty : bookId")
        }

        var response = when(type) {
            CommCode.Resource.BOOK.code -> bookService.findBook(id, CommCode.Request.REST)
            CommCode.Resource.GENRE.code -> bookService.findGenre(id, CommCode.Request.REST)
            else -> null
        }

        return BaseResponse().success(response)
    }

    @Operation(summary = "도서정보 삭제")
    @DeleteMapping("/api/{type}")
    fun delete(@PathVariable("type") type: String, @RequestParam id: String): BaseResponse {
        if(id.isNullOrBlank()){
            throw ParameterNotFoundException("Required Parameter is empty : bookId")
        }

        when(type) {
            CommCode.Resource.BOOK.code -> bookService.deleteBook(id, CommCode.Request.REST)
            CommCode.Resource.GENRE.code -> bookService.deleteGenre(id, CommCode.Request.REST)
        }

        return BaseResponse().successNoContent()
    }

    @Operation(summary = "도서정보 저장")
    @PostMapping("/api/book")
    fun saveBook(@RequestBody input: BookDto): BaseResponse {
        return BaseResponse().success(bookService.saveBook(input, CommCode.Request.REST))
    }

    @Operation(summary = "장르정보 저장")
    @PostMapping("/api/genre")
    fun saveGenre(@RequestBody input: GenreDto): BaseResponse {
        return BaseResponse().success(bookService.saveGenre(input, CommCode.Request.REST))
    }
}
