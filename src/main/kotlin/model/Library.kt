package codes.kalar.model

import kotlinx.serialization.Serializable

@Serializable
data class Library(
    val id: Long,
    val name: String,
    val address: String,
)
