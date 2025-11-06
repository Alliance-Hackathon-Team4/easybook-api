package com.team4.easybook.domain.book.application.service

import com.team4.easybook.domain.book.client.dto.request.RegisterBookContentRequest
import com.team4.easybook.domain.book.client.dto.request.RegisterBookRequest
import com.team4.easybook.domain.book.client.dto.request.UpdateProgressRequest
import com.team4.easybook.domain.book.client.dto.response.BookContentResponse
import com.team4.easybook.domain.book.client.dto.response.BookDetailResponse
import com.team4.easybook.domain.book.client.dto.response.BookListResponse
import com.team4.easybook.domain.book.domain.entity.BookContentEntity
import com.team4.easybook.domain.book.domain.entity.BookEntity
import com.team4.easybook.domain.book.domain.entity.BookProgressEntity
import com.team4.easybook.domain.book.domain.repository.BookContentJpaRepository
import com.team4.easybook.domain.book.domain.repository.BookJpaRepository
import com.team4.easybook.domain.book.domain.repository.BookProgressJpaRepository
import com.team4.easybook.global.client.DifficultyConverterClient
import com.team4.easybook.global.exception.BookContentNotFoundException
import com.team4.easybook.global.exception.BookNotFoundException
import com.team4.easybook.global.exception.ExternalApiException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
    private val bookJpaRepository: BookJpaRepository,
    private val bookProgressJpaRepository: BookProgressJpaRepository,
    private val bookContentJpaRepository: BookContentJpaRepository,
    private val difficultyConverterClient: DifficultyConverterClient
) {
    @Transactional
    fun registerBook(request: RegisterBookRequest) {
        bookJpaRepository.save(
            BookEntity(
                title = request.title,
                author = request.author,
                totalPages = request.totalPages,
                imageUrl = request.imageUrl,
            )
        )
    }

    @Transactional
    fun registerBookContent(request: RegisterBookContentRequest) {
        val book = bookJpaRepository.findByIdOrNull(request.bookId)
            ?: throw BookNotFoundException("ID가 ${request.bookId}인 책을 찾을 수 없습니다.")

        // 1. 원문(레벨 5) 저장
        bookContentJpaRepository.save(
            BookContentEntity(
                bookId = request.bookId,
                page = request.page,
                readingLevel = 5,
                content = request.content
            )
        )

        // 2. 레벨 1~4 자동 생성 및 저장
        var failedLevels = mutableListOf<Int>()
        for (level in 1..4) {
            try {
                val convertedResponse = difficultyConverterClient.convertDifficulty(level, request.content)
                if (convertedResponse != null) {
                    bookContentJpaRepository.save(
                        BookContentEntity(
                            bookId = request.bookId,
                            page = request.page,
                            readingLevel = level,
                            content = convertedResponse.convertedText
                        )
                    )
                    println("레벨 $level 변환 및 저장 완료: ${convertedResponse.levelName}")
                } else {
                    failedLevels.add(level)
                    println("레벨 $level 변환 실패")
                }
            } catch (e: Exception) {
                failedLevels.add(level)
                println("레벨 $level 저장 중 오류 발생: ${e.message}")
            }
        }

        if (failedLevels.size == 4) {
            throw ExternalApiException("모든 난이도 변환에 실패했습니다. 외부 API 서버를 확인해주세요.")
        }
    }

    @Transactional
    fun updateProgress(request: UpdateProgressRequest) {
        val book = bookJpaRepository.findByIdOrNull(request.bookId)
            ?: throw BookNotFoundException("ID가 ${request.bookId}인 책을 찾을 수 없습니다.")
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
    fun getBookById(bookId: Long): BookDetailResponse {
        val book = bookJpaRepository.findByIdOrNull(bookId)
            ?: throw BookNotFoundException("ID가 ${bookId}인 책을 찾을 수 없습니다.")

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
            ?: throw BookNotFoundException("ID가 ${bookId}인 책을 찾을 수 없습니다.")

        val content = bookContentJpaRepository.findByBookIdAndPageAndReadingLevel(bookId, page, readingLevel)
            ?: throw BookContentNotFoundException("책 ID ${bookId}, 페이지 ${page}, 난이도 ${readingLevel}에 해당하는 내용을 찾을 수 없습니다.")

        return BookContentResponse(
            page = content.page,
            readingLevel = content.readingLevel,
            content = content.content
        )
    }
}