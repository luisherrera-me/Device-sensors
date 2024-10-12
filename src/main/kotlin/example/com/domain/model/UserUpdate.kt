package example.com.domain.model

import example.com.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class UserUpdate(
    val id: String? = null,
    val name: String? = null,
    val lastName: String? = null,
    val phone: String? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime?= null,
    val profilePhoto: String? = null
)