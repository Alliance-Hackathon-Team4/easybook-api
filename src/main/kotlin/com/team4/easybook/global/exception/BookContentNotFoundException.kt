package com.team4.easybook.global.exception

class BookContentNotFoundException(message: String = "책 내용을 찾을 수 없습니다.") : RuntimeException(message)
