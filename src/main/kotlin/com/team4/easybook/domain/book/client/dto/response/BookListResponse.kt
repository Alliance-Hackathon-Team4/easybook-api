package com.team4.easybook.domain.book.client.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "책 목록 응답")
data class BookListResponse(
    @Schema(description = "책 ID", example = "1")
    val id: Long,

    @Schema(description = "책 제목", example = "어린왕자")
    val title: String,

    @Schema(description = "저자", example = "생텍쥐페리")
    val author: String,

    @Schema(description = "총 페이지 수", example = "96")
    val totalPages: Int,
)
