package example.com.domain.model

import example.com.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class SensorData (
    val id: String ?= null,
    val name: String,
    val fact: Float,
    val location: Location,
    @Serializable(with = LocalDateTimeSerializer::class)
    val date: LocalDateTime ?= null,
    val deviceId: String
)