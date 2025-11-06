package com.team4.easybook.global.client.dto

data class ConvertDifficultyResponse(
    val convertedText: String,
    val levelName: String,
    val originalText: String,
    val targetLevel: Int
)
