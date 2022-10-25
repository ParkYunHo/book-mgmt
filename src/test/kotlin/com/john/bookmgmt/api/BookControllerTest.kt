package com.john.bookmgmt.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.john.bookmgmt.dto.BookDto
import com.john.bookmgmt.dto.GenreDto
import com.john.bookmgmt.entity.Book
import com.john.bookmgmt.entity.Genre
import com.john.bookmgmt.repository.BookRepository
import com.john.bookmgmt.repository.GenreRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
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
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class BookControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var genreRepository: GenreRepository

    @BeforeAll
    fun init_dump() {
        genreRepository.save(Genre("GA001", "시", "동시"))
        genreRepository.save(Genre("GA002", "시", "고대시가"))
        genreRepository.save(Genre("GB001", "기사", "신문기사"))
        genreRepository.save(Genre("GB002", "기사", "잡지기사"))
        bookRepository.save(Book(
            bookId = "B001",
            genre = Genre("GA001", "시", "동시"),
            bookName = "아기잠",
            updatedAt = LocalDateTime.now()
        ))
    }

    @Test
    @DisplayName("1. 조회 성공테스트")
    fun TEST_find() {
        // Given
        val bookId = "B001"
        val genreCode = "GA001"

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
        // then2 : 장르조회
        mockMvc.perform(builder2)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }

    @Test
    @DisplayName("2. 삭제 성공테스트")
    fun TEST_delete() {
        // Given
        val bookId = "B001"
        val genreCode = "GA001"

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
        // then2 : 장르조회
        mockMvc.perform(builder2)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }

    @Test
    @DisplayName("3. 도서 저장 성공테스트")
    fun TEST_saveBook() {
        // Given
        val input = BookDto(
            bookId = "B001",
            genreCode = "GA001",
            bookName = "아기잠",
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
    }

    @Test
    @DisplayName("4. 장르 저장 성공테스트")
    fun TEST_saveGenre() {
        // Given
        val input = GenreDto(
            genreCode = "GA001",
            genreMainCategory = "시",
            genreSubCategory = "동시"
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
    }
}