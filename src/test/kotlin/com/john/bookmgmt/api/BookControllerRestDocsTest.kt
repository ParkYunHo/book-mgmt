package com.john.bookmgmt.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.john.bookmgmt.common.Constants
import com.john.bookmgmt.dto.BookDto
import com.john.bookmgmt.dto.GenreDto
import com.john.bookmgmt.entity.Book
import com.john.bookmgmt.entity.Genre
import com.john.bookmgmt.repository.BookRepository
import com.john.bookmgmt.repository.GenreRepository
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2022.10.26
 */
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestMethodOrder(MethodOrderer.DisplayName::class)
internal class BookControllerRestDocsTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var genreRepository: GenreRepository

    @BeforeEach
    fun init_dump() {
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

    @Test
    @DisplayName("1. 조회 성공테스트")
    fun TEST_find() {
        // Given
        val bookId = Constants.TEST_BOOK_ID.code

        // When
        val builder1: MockHttpServletRequestBuilder = MockMvcRequestBuilders
            .get("/api/book")
            .param("id", bookId)

        // then1 : 도서조회
        mockMvc.perform(builder1)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Success"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
            .andDo(document(
                "book",
                requestParameters(parameterWithName("id").description("unique key")),
                responseFields(
                    fieldWithPath("status").description("response status"),
                    fieldWithPath("message").description("response message"),
                    fieldWithPath("data").description("response data"),
                    fieldWithPath("data.bookId").description("bookId"),
                    fieldWithPath("data.genreCode").description("genreCode"),
                    fieldWithPath("data.genreMainCategory").description("genreMainCategory"),
                    fieldWithPath("data.genreSubCategory").description("genreSubCategory"),
                    fieldWithPath("data.bookName").description("bookName"),
                    fieldWithPath("data.updatedAt").description("updatedAt"),
                    fieldWithPath("data.createdAt").description("createdAt"),
                )
            ))
    }
}