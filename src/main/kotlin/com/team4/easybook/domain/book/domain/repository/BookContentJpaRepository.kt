package com.team4.easybook.domain.book.domain.repository

import com.team4.easybook.domain.book.domain.entity.BookContentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BookContentJpaRepository : JpaRepository<BookContentEntity, Long> {
    fun findByBookIdAndPage(bookId: Long, page: Int): BookContentEntity?
    fun findByBookIdAndPageAndReadingLevel(bookId: Long, page: Int, readingLevel: Int): BookContentEntity?
    fun findByBookId(bookId: Long): List<BookContentEntity>
}