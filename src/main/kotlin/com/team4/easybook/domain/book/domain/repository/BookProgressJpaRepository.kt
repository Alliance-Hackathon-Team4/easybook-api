package com.team4.easybook.domain.book.domain.repository

import com.team4.easybook.domain.book.domain.entity.BookProgressEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BookProgressJpaRepository : JpaRepository<BookProgressEntity, Long> {
    fun findByBookId(bookId: Long): BookProgressEntity?
}