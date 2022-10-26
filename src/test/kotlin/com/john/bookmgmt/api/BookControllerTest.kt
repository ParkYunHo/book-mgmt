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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
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
@TestMethodOrder(MethodOrderer.DisplayName::class)
internal class BookControllerTest {
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
        val genreCode = Constants.TEST_GENRE_CODE.code

        // When
        val builder1: MockHttpServletRequestBuilder = MockMvcRequestBuilders
            .get("/api/book")
            .param("id", bookId)
        val builder2: MockHttpServletRequestBuilder = MockMvcRequestBuilders
            .get("/api/genre")
            .param("id", genreCode)

        // then1 : 도서조회
        mockMvc.perform(builder1)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Success"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
        // then2 : 장르조회
        mockMvc.perform(builder2)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Success"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
    }

    @Test
    @DisplayName("2. 삭제 성공테스트")
    fun TEST_delete() {
        // Given
        val bookId = Constants.TEST_BOOK_ID.code
        val genreCode = Constants.TEST_GENRE_CODE.code

        // When
        val builder1: MockHttpServletRequestBuilder = MockMvcRequestBuilders
            .delete("/api/book")
            .param("id", bookId)
        val builder2: MockHttpServletRequestBuilder = MockMvcRequestBuilders
            .delete("/api/genre")
            .param("id", genreCode)

        // then1 : 도서조회
        mockMvc.perform(builder1)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Success"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty)
        // then2 : 장르조회
        mockMvc.perform(builder2)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Success"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty)
    }

    @Test
    @DisplayName("3. 도서 저장 성공테스트")
    fun TEST_saveBook() {
        // Given
        val input = BookDto(
            bookId = Constants.TEST_BOOK_ID.code,
            genreCode = Constants.TEST_GENRE_CODE.code,
            bookName = Constants.TEST_BOOK_NAME.code,
            genreSubCategory = null, genreMainCategory = null, updatedAt = null, createdAt = null
        )
        val om = ObjectMapper()
        val param = om.writeValueAsString(input)

        // When
        val builder: MockHttpServletRequestBuilder = MockMvcRequestBuilders
            .post("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(param)

        // then
        mockMvc.perform(builder)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Success"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.bookId").value(Constants.TEST_BOOK_ID.code))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.genreCode").value(Constants.TEST_GENRE_CODE.code))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.bookName").value(Constants.TEST_BOOK_NAME.code))
    }

    @Test
    @DisplayName("4. 장르 저장 성공테스트")
    fun TEST_saveGenre() {
        // Given
        val input = GenreDto(
            genreCode = Constants.TEST_GENRE_CODE.code,
            genreMainCategory = Constants.TEST_GENRE_MAIN_CATEGORY.code,
            genreSubCategory = Constants.TEST_GENRE_SUB_CATEGORY.code
        )
        val om = ObjectMapper()
        val param = om.writeValueAsString(input)

        // When
        val builder: MockHttpServletRequestBuilder = MockMvcRequestBuilders
            .post("/api/genre")
            .contentType(MediaType.APPLICATION_JSON)
            .content(param)

        // then
        mockMvc.perform(builder)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Success"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.genreCode").value(Constants.TEST_GENRE_CODE.code))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.genreMainCategory").value(Constants.TEST_GENRE_MAIN_CATEGORY.code))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.genreSubCategory").value(Constants.TEST_GENRE_SUB_CATEGORY.code))
    }
}