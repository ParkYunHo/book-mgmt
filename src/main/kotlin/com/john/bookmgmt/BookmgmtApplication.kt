package com.john.bookmgmt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BookmgmtApplication

fun main(args: Array<String>) {
    runApplication<BookmgmtApplication>(*args)
}
