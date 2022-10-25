package com.john.bookmgmt.api

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
internal class BookControllerFailTest {
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
        ));
    }

    @Test
    @DisplayName("1. 조회 실패테스트")
    fun TEST_find_fail() {
        // Given
        val bookId = "TEST"
        val genreCode = "TEST"

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
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
        // then2 : 장르조회
        mockMvc.perform(builder2)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    @DisplayName("2. 삭제 실패테스트")
    fun TEST_delete_fail() {
        // Given
        val bookId = "TEST"
        val genreCode = "TEST"

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
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
        // then2 : 장르조회
        mockMvc.perform(builder2)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }
}