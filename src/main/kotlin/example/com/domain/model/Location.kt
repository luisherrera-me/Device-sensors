package example.com.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Location (
    val longitude: String,
    val latitude: String
)