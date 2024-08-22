package example.com.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WidgetSensor (
    val id: String,
    val name: String,
    val fact: Float,
    val date: Long,
    val idDevice: String
)