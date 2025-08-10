package codes.kalar.model

import kotlinx.serialization.Serializable

@Serializable
data class CollectionItem(
    val id: Long,
    val title: String = "",
    val author: String = "",
    val publisher: String = "",
    val publishingDate: String = "9999-12-31",
    val locNumber: String = "",
    val deweyDecimalNumber: String = "",
    val isbn: Long = 0L,
    val sortTitle: String = "",
    val format: String = "",
    val language: String = "",
    val pageCount: Int = 0,
    val categories: String = "",
    val description: String = "blank",
    val priceInCents: Int = 0,
    val coverImageUri: String = "default",
    val isCheckedIn: Boolean = true,
    val isArchived: Boolean = false,
    val isLost: Boolean = false,
    val lostDate: String = "9999-12-31",
)


@Serializable
data class NewCollectionItem(
    // ID to be inserted by Database
    val title: String = "",
    val author: String = "",
    val publisher: String = "",
    val publishingDate: String = "9999-12-31",
    val locNumber: String = "",
    val deweyDecimalNumber: String = "",
    val isbn: Long = 0L,
    val sortTitle: String = "",
    val format: String = "",
    val language: String = "",
    val pageCount: Int = 0,
    val categories: String = "",
    val description: String = "blank",
    val priceInCents: Int = 0,
    val coverImageUri: String = "default",
    val isCheckedIn: Boolean = true,
    val isArchived: Boolean = false,
    val isLost: Boolean = false,
    val lostDate: String = "9999-12-31",
)