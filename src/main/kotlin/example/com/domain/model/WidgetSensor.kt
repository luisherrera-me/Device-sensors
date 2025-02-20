package example.com.domain.model

import example.com.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Widget(
    val id: String,
    val userId: String,
    val name: String,
    val layout: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime?= null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime?= null,
)

data class WidgetDevice(
    val widgetId: String,
    val deviceId: String,
    val displayOptions: DisplayOptions
)

data class DisplayOptions(
    val showLastReading: Boolean,
    val showGraph: Boolean
)
