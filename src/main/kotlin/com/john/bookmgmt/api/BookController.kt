package com.john.bookmgmt.api

import com.john.bookmgmt.api.dto.BaseResponse
import com.john.bookmgmt.constants.CommCode
import com.john.bookmgmt.dto.BookDto
import com.john.bookmgmt.dto.GenreDto
import com.john.bookmgmt.exception.ParameterNotFoundException
import com.john.bookmgmt.service.BookService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * @author yoonho
 * @since 2022.10.26
 */
@RestController
class BookController(
    private val bookService: BookService
) {

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

    @PostMapping("/api/book")
    fun saveBook(@RequestBody input: BookDto): BaseResponse {
        return BaseResponse().success(bookService.saveBook(input, CommCode.Request.REST))
    }

    @PostMapping("/api/genre")
    fun saveGenre(@RequestBody input: GenreDto): BaseResponse {
        return BaseResponse().success(bookService.saveGenre(input, CommCode.Request.REST))
    }
}
