package cmps312.qatar2022

import kotlinx.serialization.Serializable

@Serializable
data class Stadium(
    val name: String,
    val city: String,
    val status: String
)