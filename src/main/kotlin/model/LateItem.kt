package codes.kalar.model

import kotlinx.serialization.Serializable

@Serializable
data class LateItem(
    val id: Long,
    val itemId: Long,
    val patronId: Long,
    val dueDate: String,
    var feeInCents: Int,
)
