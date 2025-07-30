package codes.kalar.model

import kotlinx.serialization.Serializable

@Serializable
data class LostItem(
    val id: Long,
    val itemId: Long,
    val patronId: Long,
    val dueDate: String,
    val costInCents: Int,
)
