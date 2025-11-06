package com.team4.easybook.global.exception

class ExternalApiException(message: String = "외부 API 호출 중 오류가 발생했습니다.") : RuntimeException(message)
