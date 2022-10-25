package com.john.bookmgmt.repository

import com.querydsl.jpa.impl.JPAQueryFactory

/**
 * @author yoonho
 * @since 2022.10.26
 */
class BookRepositoryImp(
    private val query: JPAQueryFactory
): BookRepositoryDsl {

}