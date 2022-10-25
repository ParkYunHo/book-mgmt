package com.john.bookmgmt.repository

import com.john.bookmgmt.entity.Book
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author yoonho
 * @since 2022.10.26
 */
interface BookRepository: JpaRepository<Book, String>, BookRepositoryDsl

interface BookRepositoryDsl {

}