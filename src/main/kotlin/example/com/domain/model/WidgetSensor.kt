package example.com.domain.model

import example.com.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class WidgetSensor (
    val id: String,
    val name: String,
    val fact: Float,
    @Serializable(with = LocalDateTimeSerializer::class)
    val date: LocalDateTime ?= null,
    val idDevice: String
)