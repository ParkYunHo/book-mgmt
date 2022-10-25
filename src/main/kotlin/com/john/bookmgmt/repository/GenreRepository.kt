package com.john.bookmgmt.repository

import com.john.bookmgmt.entity.Genre
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author yoonho
 * @since 2022.10.26
 */
interface GenreRepository : JpaRepository<Genre, String>