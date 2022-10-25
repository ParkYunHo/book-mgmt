package com.john.bookmgmt.api

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
internal class BookControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @DisplayName("1. 도서 조회")
    fun TEST_findBook() {
        // Given

        // When
        val builder: MockHttpServletRequestBuilder = MockMvcRequestBuilders
            .get("/")

        // then
        mockMvc.perform(builder)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }
}