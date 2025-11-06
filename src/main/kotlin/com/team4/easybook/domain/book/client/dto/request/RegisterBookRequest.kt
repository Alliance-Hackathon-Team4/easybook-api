package com.team4.easybook.domain.book.client.dto.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "책 등록 요청")
data class RegisterBookRequest(
    @Schema(description = "책 제목", example = "어린왕자")
    val title: String,

    @Schema(description = "저자", example = "생텍쥐페리")
    val author: String,

    @Schema(description = "총 페이지 수", example = "96")
    val totalPages: Int,

    val imageUrl: String,
)
