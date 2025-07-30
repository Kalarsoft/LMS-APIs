package codes.kalar.model

import kotlinx.serialization.Serializable

@Serializable
data class LibraryCollection(
    val id: Long,
    val libraryId: Long,
    val itemId: Long,
)
