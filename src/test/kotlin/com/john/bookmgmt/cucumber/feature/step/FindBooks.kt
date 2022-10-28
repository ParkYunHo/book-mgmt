package com.john.bookmgmt.cucumber.feature.step

import com.john.bookmgmt.common.Constants
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
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime

class FindBooks {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var genreRepository: GenreRepository

    private var resultActions: ResultActions? = null
    private var id: String = ""

    @Before(value = "@find-book")
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

    @먼저("도서정보를 구분하기 위한 {string} 가 있다")
    fun 도서정보를_구분하기_위한_가_있다(id: String){
        logger.info(" >>> [Given] id: {}", id)

        this.id = id
    }

    @만약("도서정보와 함께 도서 조회를 {string} 요청한다면")
    fun 도서정보와_함께_도서_조회를_요청한다면(url: String) {
        logger.info(" >>> [When] url: {}", url)

        val builder: MockHttpServletRequestBuilder = MockMvcRequestBuilders
            .get(url)
            .contentType(MediaType.APPLICATION_JSON)
            .param("id", id)
        resultActions = mockMvc.perform(builder)
    }

    @그러면("조회된 도서정보 결과 {int}{string} 를 확인한다")
    fun 조회된_도서정보_결과_를_확인한다(code: Int, status: String) {
        resultActions?.andDo(MockMvcResultHandlers.print())
            ?.andExpect(MockMvcResultMatchers.status().`is`(code))
            ?.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(status))
    }
}