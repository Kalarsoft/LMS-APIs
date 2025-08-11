package codes.kalar.model

import kotlinx.serialization.Serializable

@Serializable
data class Staff(
    val id: Long,
    val name: String,
    val password: String?,
    val isArchived: Boolean,
)

@Serializable
data class NewStaff(
    // ID to be inserted by Database
    val name: String,
    val password: String?,
    val isArchived: Boolean,
)
