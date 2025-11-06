package com.team4.easybook.domain.book.client.dto.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "책 내용 등록 요청")
data class RegisterBookContentRequest(
    @Schema(description = "책 ID", example = "1")
    val bookId: Long,

    @Schema(description = "페이지 번호", example = "1")
    val page: Int,

    @Schema(description = "읽기 난이도 (1~5)", example = "3")
    val readingLevel: Int,

    @Schema(description = "페이지 내용 (원문)", example = "옛날 옛적에 어느 작은 별에 어린 왕자가 살고 있었습니다...")
    val content: String
)
