package com.team4.easybook.domain.book.client.dto.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "책 읽기 진행 상황 업데이트 요청")
data class UpdateProgressRequest(
    @Schema(description = "책 ID", example = "1")
    val bookId: Long,

    @Schema(description = "현재 읽은 페이지", example = "42")
    val page: Int,

    @Schema(description = "읽기 난이도 (1~5)", example = "3")
    val readingLevel: Int
)
