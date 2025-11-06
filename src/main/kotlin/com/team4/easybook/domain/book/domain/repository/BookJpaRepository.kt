package com.team4.easybook.domain.book.domain.repository

import com.team4.easybook.domain.book.domain.entity.BookEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookJpaRepository : JpaRepository<BookEntity, Long>