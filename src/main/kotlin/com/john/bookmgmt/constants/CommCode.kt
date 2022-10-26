package com.john.bookmgmt.constants

/**
 * @author yoonho
 * @since 2022.10.26
 */
class CommCode {
    enum class Resource(val code: String){
        BOOK("book"),
        GENRE("genre")
    }

    enum class Request(val code: String){
        REST("rest"),
        GRAPHQL("graphql")
    }
}