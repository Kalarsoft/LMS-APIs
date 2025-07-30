package codes.kalar.model

import kotlinx.serialization.Serializable

@Serializable
data class OnHoldItem(
    val id: Long,
    val itemId: Long,
    val patronId: Long,
    val holdReleaseDate: String,
)
