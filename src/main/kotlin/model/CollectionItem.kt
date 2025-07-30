package codes.kalar.model

import kotlinx.serialization.Serializable

@Serializable
data class CollectionItem(
    val id: Long,
    val title: String?,
    val author: String?,
    val publisher: String?,
    val publishingDate: String?,
    val locNumber: String?,
    val deweyDecimalNumber: String?,
    val isbn: Int?,
    val sortTitle: String?,
    val format: String?,
    val language: String?,
    val pageCount: Int?,
    val category: String?,
    val description: String?,
    val priceInCents: Int?,
    val coverImageUri: String?,
    val isCheckedIn: Boolean,
    val isArchived: Boolean,
    val isLost: Boolean,
    val lostDate: String?,
)
