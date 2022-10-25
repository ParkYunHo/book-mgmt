package com.john.bookmgmt.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController {

    @GetMapping("/")
    fun findBook(): String {
        return "init"
    }
}
