package codes.kalar.service

import codes.kalar.exception.DbElementInsertionException
import codes.kalar.exception.DbElementNotFoundException
import kotlinx.serialization.json.Json
import java.sql.Connection

class StaffService(private val connection: Connection) {

    companion object {
        private const val SELECT_STAFF_BY_ID = ""
        private const val INSERT_STAFF = ""
        private const val UPDATE_STAFF_BY_ID = ""
        // In the event  are "deleted" erroneously, having a flag set instead of actually removing the entry allows
        // for quick reversal.
        private const val ARCHIVE_STAFF_BY_ID = ""
    }

    suspend fun create() {}

    suspend fun read() {}

    suspend fun update() {}

    suspend fun delete() {}
}