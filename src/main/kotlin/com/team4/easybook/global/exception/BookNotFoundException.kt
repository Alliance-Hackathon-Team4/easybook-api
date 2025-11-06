package com.team4.easybook.global.exception

class BookNotFoundException(message: String = "책을 찾을 수 없습니다.") : RuntimeException(message)
