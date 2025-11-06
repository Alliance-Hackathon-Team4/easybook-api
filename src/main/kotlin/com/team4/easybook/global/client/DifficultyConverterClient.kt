package com.team4.easybook.global.client

import com.team4.easybook.global.client.dto.ConvertDifficultyRequest
import com.team4.easybook.global.client.dto.ConvertDifficultyResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class DifficultyConverterClient(
    private val webClient: WebClient
) {
    fun convertDifficulty(targetLevel: Int, text: String): ConvertDifficultyResponse? {
        return try {
            webClient.post()
                .uri("/convert")
                .bodyValue(
                    mapOf(
                        "target_level" to targetLevel,
                        "text" to text
                    )
                )
                .retrieve()
                .bodyToMono(Map::class.java)
                .map { response ->
                    ConvertDifficultyResponse(
                        convertedText = response["converted_text"] as String,
                        levelName = response["level_name"] as String,
                        originalText = response["original_text"] as String,
                        targetLevel = (response["target_level"] as Number).toInt()
                    )
                }
                .block()
        } catch (e: Exception) {
            println("Error converting difficulty for level $targetLevel: ${e.message}")
            null
        }
    }
}
