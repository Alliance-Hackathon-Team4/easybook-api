package com.team4.easybook.domain.book.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class BookContentEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val bookId: Long,

    // 페이지와 읽기 난이도(1~5)를 구분으로 다른 내용이 저장되도록 함.
    val page: Int,
    val readingLevel: Int,

    // 해당 페이지에 저장된 내용
    @Column(columnDefinition = "TEXT")
    val content: String
)
