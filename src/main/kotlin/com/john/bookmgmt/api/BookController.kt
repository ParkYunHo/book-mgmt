package com.john.bookmgmt.api

import com.john.bookmgmt.api.dto.BaseResponse
import com.john.bookmgmt.constants.CommCode
import com.john.bookmgmt.dto.BookDto
import com.john.bookmgmt.dto.GenreDto
import com.john.bookmgmt.service.BookService
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
    fun find(@PathVariable("type") type: String, id: String): BaseResponse {

        var response = when(type) {
            CommCode.Resource.BOOK.code -> bookService.findBook(id)
            CommCode.Resource.GENRE.code -> bookService.findGenre(id)
            else -> null
        }

        return BaseResponse().success(response)
    }

    @DeleteMapping("/api/{type}")
    fun delete(@PathVariable("type") type: String, id: String): BaseResponse {

        when(type) {
            CommCode.Resource.BOOK.code -> bookService.deleteBook(id)
            CommCode.Resource.GENRE.code -> bookService.deleteGenre(id)
        }

        return BaseResponse().successNoContent()
    }

    @PostMapping("/api/book")
    fun saveBook(@RequestBody input: BookDto): BaseResponse {
        return BaseResponse().success(bookService.saveBook(input))
    }

    @PostMapping("/api/genre")
    fun saveGenre(@RequestBody input: GenreDto): BaseResponse {
        return BaseResponse().success(bookService.saveGenre(input))
    }
}
