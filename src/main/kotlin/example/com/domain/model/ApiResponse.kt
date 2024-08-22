package example.com.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseError(
    val statusCode: Int = 500,
    val message: String = ""
)

