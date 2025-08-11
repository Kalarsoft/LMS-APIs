package codes.kalar.model

import kotlinx.serialization.Serializable

@Serializable
data class Patron(
    val id: Long,
    val name: String,
    val hasGoodStanding: Boolean,
    val feeTotal: Long,
    val isArchived: Boolean,
    val lastLogin: String?,
    val password: String?,
)

@Serializable
data class NewPatron(
    // ID to be inserted by Database
    val name: String,
    val hasGoodStanding: Boolean,
    val feeTotal: Long,
    val isArchived: Boolean,
    val lastLogin: String?,
    val password: String?,
)
