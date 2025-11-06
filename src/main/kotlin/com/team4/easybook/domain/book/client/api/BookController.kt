package com.team4.easybook.domain.book.client.api

import com.team4.easybook.domain.book.application.service.BookService
import com.team4.easybook.domain.book.client.dto.request.RegisterBookContentRequest
import com.team4.easybook.domain.book.client.dto.request.RegisterBookRequest
import com.team4.easybook.domain.book.client.dto.request.UpdateProgressRequest
import com.team4.easybook.domain.book.client.dto.response.BookContentResponse
import com.team4.easybook.domain.book.client.dto.response.BookDetailResponse
import com.team4.easybook.domain.book.client.dto.response.BookListResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Book", description = "책 관리 API")
@RestController
@RequestMapping("/books")
class BookController(
    private val bookService: BookService
) {
    @Operation(summary = "책 등록", description = "새로운 책을 등록합니다.")
    @PostMapping
    fun registerBook(@RequestBody request: RegisterBookRequest): ResponseEntity<Void> {
        bookService.registerBook(request)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "전체 책 목록 조회", description = "등록된 모든 책의 목록을 조회합니다.")
    @GetMapping
    fun getAllBooks(): ResponseEntity<List<BookListResponse>> {
        val books = bookService.getAllBooks()
        return ResponseEntity.ok(books)
    }

    @Operation(summary = "책 상세 조회", description = "책의 상세 정보와 진행 상황을 조회합니다.")
    @GetMapping("/{bookId}")
    fun getBookById(
        @Parameter(description = "책 ID", required = true) @PathVariable bookId: Long,
    ): ResponseEntity<BookDetailResponse> {
        val book = bookService.getBookById(bookId)
        return ResponseEntity.ok(book)
    }

    @Operation(summary = "책 내용 등록", description = "특정 페이지의 책 내용(원문)을 등록합니다.")
    @PostMapping("/{bookId}/contents")
    fun registerBookContent(@RequestBody request: RegisterBookContentRequest): ResponseEntity<Void> {
        bookService.registerBookContent(request)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "책 내용 조회", description = "특정 페이지의 책 내용을 조회합니다.")
    @GetMapping("/{bookId}/contents")
    fun getBookContent(
        @Parameter(description = "책 ID", required = true) @PathVariable bookId: Long,
        @Parameter(description = "페이지 번호", required = true) @RequestParam page: Int,
        @Parameter(description = "읽기 난이도 (1~5)", required = true) @RequestParam readingLevel: Int
    ): ResponseEntity<BookContentResponse> {
        val content = bookService.getBookContent(bookId, page, readingLevel)
        return ResponseEntity.ok(content)
    }

    @Operation(summary = "진행 상황 업데이트", description = "책 읽기 진행 상황을 업데이트합니다.")
    @PutMapping("/{bookId}/progress")
    fun updateProgress(@RequestBody request: UpdateProgressRequest): ResponseEntity<Void> {
        bookService.updateProgress(request)
        return ResponseEntity.ok().build()
    }
}
