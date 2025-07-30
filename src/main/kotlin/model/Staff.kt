package codes.kalar.model

import kotlinx.serialization.Serializable

@Serializable
data class Staff(
    val id: Long,
    val name: String,
    val password: String?,
    val isArchived: Boolean,
)
