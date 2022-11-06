package com.john.bookmgmt.karate.book

import com.intuit.karate.junit5.Karate

class BookRunner {

    @Karate.Test
    fun save(): Karate {
        return Karate.run("save-book").relativeTo(javaClass)
    }

    @Karate.Test
    fun find(): Karate {
        return Karate.run("find-book").relativeTo(javaClass)
    }
}