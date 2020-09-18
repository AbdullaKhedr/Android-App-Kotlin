package cmps312.lab.qatar2022.model

import kotlinx.serialization.Serializable

@Serializable
data class Stadium(
    val name: String,
    val city: String,
    val status: String,
    val image: String
)