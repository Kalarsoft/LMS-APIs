package codes.kalar.service

import codes.kalar.exception.DbElementInsertionException
import codes.kalar.exception.DbElementNotFoundException
import kotlinx.serialization.json.Json
import java.sql.*

class LibraryService(private val connection: Connection) {

    companion object {
        private const val SELECT_LIBRARY_BY_ID = ""
        private const val INSERT_LIBRARY = ""
        private const val UPDATE_LIBRARY_BY_ID = ""
        // In the event  are "deleted" erroneously, having a flag set instead of actually removing the entry allows
        // for quick reversal.
        private const val ARCHIVE_LIBRARY_BY_ID = ""
    }

    suspend fun create() {}

    suspend fun read() {}

    suspend fun update() {}

    suspend fun delete() {}
}
