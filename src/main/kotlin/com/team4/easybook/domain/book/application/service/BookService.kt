package com.team4.easybook.domain.book.application.service

import com.team4.easybook.domain.book.client.dto.request.RegisterBookRequest
import com.team4.easybook.domain.book.client.dto.request.UpdateProgressRequest
import com.team4.easybook.domain.book.client.dto.response.BookContentResponse
import com.team4.easybook.domain.book.client.dto.response.BookDetailResponse
import com.team4.easybook.domain.book.client.dto.response.BookListResponse
import com.team4.easybook.domain.book.domain.entity.BookEntity
import com.team4.easybook.domain.book.domain.entity.BookProgressEntity
import com.team4.easybook.domain.book.domain.repository.BookContentJpaRepository
import com.team4.easybook.domain.book.domain.repository.BookJpaRepository
import com.team4.easybook.domain.book.domain.repository.BookProgressJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
    private val bookJpaRepository: BookJpaRepository,
    private val bookProgressJpaRepository: BookProgressJpaRepository,
    private val bookContentJpaRepository: BookContentJpaRepository
) {
    @Transactional
    fun registerBook(request: RegisterBookRequest) {
        bookJpaRepository.save(
            BookEntity(
                title = request.title,
                author = request.author,
                totalPages = request.totalPages,
            )
        )
    }

    @Transactional
    fun updateProgress(request: UpdateProgressRequest) {
        val book = bookJpaRepository.findByIdOrNull(request.bookId) ?: throw IllegalArgumentException("찾을 수 없는 책입니다.")
        val bookProgress = bookProgressJpaRepository.findByBookId(book.id!!)
            ?: bookProgressJpaRepository.save(
                BookProgressEntity(userId = 1L, bookId = request.bookId, page = 0, readingLevel = request.readingLevel)
            )

        bookProgressJpaRepository.save(bookProgress.copy(
            page = request.page,
        ))
    }

    @Transactional(readOnly = true)
    fun getAllBooks(): List<BookListResponse> {
        return bookJpaRepository.findAll().map { book ->
            BookListResponse(
                id = book.id!!,
                title = book.title,
                author = book.author,
                totalPages = book.totalPages
            )
        }
    }

    @Transactional(readOnly = true)
    fun getBookById(bookId: Long, userId: Long): BookDetailResponse {
        val book = bookJpaRepository.findByIdOrNull(bookId)
            ?: throw IllegalArgumentException("찾을 수 없는 책입니다.")

        val bookProgress = bookProgressJpaRepository.findByBookId(bookId)

        return BookDetailResponse(
            id = book.id!!,
            title = book.title,
            author = book.author,
            totalPages = book.totalPages,
            currentPage = bookProgress?.page,
            currentReadingLevel = bookProgress?.readingLevel
        )
    }

    @Transactional(readOnly = true)
    fun getBookContent(bookId: Long, page: Int, readingLevel: Int): BookContentResponse {
        val book = bookJpaRepository.findByIdOrNull(bookId)
            ?: throw IllegalArgumentException("찾을 수 없는 책입니다.")

        val content = bookContentJpaRepository.findByBookIdAndPage(bookId, page)
            ?: throw IllegalArgumentException("해당 페이지의 내용을 찾을 수 없습니다.")

        return BookContentResponse(
            page = content.page,
            readingLevel = content.readingLevel,
            content = content.content
        )
    }
}