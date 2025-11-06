package com.team4.easybook.domain.book.client.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "책 상세 정보 응답")
data class BookDetailResponse(
    @Schema(description = "책 ID", example = "1")
    val id: Long,

    @Schema(description = "책 제목", example = "어린왕자")
    val title: String,

    @Schema(description = "저자", example = "생텍쥐페리")
    val author: String,

    @Schema(description = "총 페이지 수", example = "96")
    val totalPages: Int,

    @Schema(description = "현재 읽은 페이지", example = "42", nullable = true)
    val currentPage: Int?,

    @Schema(description = "현재 읽기 난이도 (1~5)", example = "3", nullable = true)
    val currentReadingLevel: Int?
)

@Schema(description = "책 내용 응답")
data class BookContentResponse(
    @Schema(description = "페이지 번호", example = "1")
    val page: Int,

    @Schema(description = "읽기 난이도 (1~5)", example = "3")
    val readingLevel: Int,

    @Schema(description = "페이지 내용", example = "옛날 옛적에...")
    val content: String
)
