package com.john.bookmgmt.cucumber.feature.step.test

import com.john.bookmgmt.common.Constants
import com.john.bookmgmt.entity.Book
import com.john.bookmgmt.entity.Genre
import com.john.bookmgmt.repository.BookRepository
import com.john.bookmgmt.repository.GenreRepository
import io.cucumber.java.Before
import io.cucumber.java.ko.그러면
import io.cucumber.java.ko.만약
import io.cucumber.java.ko.먼저
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime

class TestBdd {

    private var t1: Int = 0

    @먼저("기본적인 값인 {int} 을 세팅한다")
    fun 기본적인_값인_을_세팅한다(paramVal: Int){
        this.t1 = paramVal
    }

    @만약("해당 값을 2배로 곱한다면")
    fun 해당_값을_2배로_곱한다면() {
        t1 = t1 * 2
    }

    @그러면("해당 값의 2배인 {int} 를 확인할 수 있다")
    fun 해당_값의_2배인_를_확인할_수_있다(result: Int) {
        Assertions.assertEquals(result, t1)
    }
}