package codes.kalar.model

import kotlinx.serialization.Serializable

@Serializable
data class CheckedOutItem(
    val id: Long,
    val itemId: Long,
    val patronId: Long,
    val dueDate: String,
)