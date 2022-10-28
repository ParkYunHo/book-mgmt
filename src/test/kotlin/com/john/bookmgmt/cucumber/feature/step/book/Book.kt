package com.john.bookmgmt.cucumber.feature.step.book

import com.fasterxml.jackson.databind.ObjectMapper
import com.john.bookmgmt.common.Constants
import com.john.bookmgmt.dto.BookDto
import com.john.bookmgmt.dto.GenreDto
import com.john.bookmgmt.entity.Book
import com.john.bookmgmt.entity.Genre
import com.john.bookmgmt.repository.BookRepository
import com.john.bookmgmt.repository.GenreRepository
import io.cucumber.java.Before
import io.cucumber.java.ko.그러면
import io.cucumber.java.ko.만약
import io.cucumber.java.ko.먼저
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.net.URI
import java.net.URL
import java.time.LocalDateTime

class Book {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var genreRepository: GenreRepository

    private var resultActions: ResultActions? = null

    private var id: String? = null
    private var input: String? = null

    @Before(value = "@book")
    fun setup() {
        var genre = Genre(
            genreCode = Constants.TEST_GENRE_CODE.code,
            genreMainCategory = Constants.TEST_GENRE_MAIN_CATEGORY.code,
            genreSubCategory = Constants.TEST_GENRE_SUB_CATEGORY.code
        )

        genreRepository.save(genre)
        bookRepository.save(
            Book(
                bookId = Constants.TEST_BOOK_ID.code,
                genre = genre,
                bookName = Constants.TEST_BOOK_NAME.code,
                updatedAt = LocalDateTime.now()
            )
        )
    }

    @먼저("도서관리API 호출을 위한 {string}{string}{string}{string}{string}{string} 가 있다")
    fun 도서관리API_호출을_위한_가_있다(type: String, bookId: String, genreCode: String, genreMainCategory: String, genreSubCategory: String, bookName: String){
        val om = ObjectMapper()
        if(type.equals("book")){
            id = bookId

            var bookDto = BookDto(
                bookId = bookId,
                genreCode = genreCode,
                genreMainCategory = genreMainCategory,
                genreSubCategory = genreSubCategory,
                bookName = bookName,
                updatedAt = null,
                createdAt = null
            )
            input = om.writeValueAsString(bookDto)
        }else if(type.equals("genre")){
            id = genreCode

            var genreDto = GenreDto(
                genreCode = genreCode,
                genreMainCategory = genreMainCategory,
                genreSubCategory = genreSubCategory
            )
            input = om.writeValueAsString(genreDto)

            // FK설정때문에 BOOK테이블 데이터 삭제
            bookRepository.deleteById(Constants.TEST_BOOK_ID.code)
            //
        }
    }

    @만약("도서정보와 함께 도서관리API를 {string}{string} 요청하면")
    fun 도서정보와_함께_도서관리API를_요청하면(method: String, url: String) {
        val builder: MockHttpServletRequestBuilder = MockMvcRequestBuilders
            .request(method, URI(url))
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.input!!)
            .param("id", id)
        resultActions = mockMvc.perform(builder)
    }

    @그러면("도서관리API 호출결과 {int}{string} 를 확인한다")
    fun 도서관리API_호출결과_를_확인한다(code: Int, status: String) {
        resultActions?.andDo(MockMvcResultHandlers.print())
            ?.andExpect(MockMvcResultMatchers.status().`is`(code))
            ?.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(status))
    }
}