package com.team4.easybook.domain.book.domain.entity

import jakarta.persistence.*

@Entity
data class BookProgressEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val userId: Long,
    val bookId: Long,

    // 마지막으로 읽은 페이지
    val page: Int,
    // 마지막으로 설정한 읽기 난이도
    val readingLevel: Int,
)
