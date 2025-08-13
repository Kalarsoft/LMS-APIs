package codes.kalar.service

import codes.kalar.exception.DbElementInsertionException
import codes.kalar.exception.DbElementNotFoundException
import codes.kalar.model.Library
import codes.kalar.model.NewLibrary
import kotlinx.serialization.json.Json
import java.sql.*

class LibraryService(private val connection: Connection) {

    companion object {
        private const val SELECT_LIBRARY_BY_ID = "SELECT * FROM library WHERE  id = ?"
        private const val SELECT_LIBRARY_ALL = "SELECT * FROM library ORDER BY id DESC LIMIT 25"
        private const val INSERT_LIBRARY = "INSERT INTO library (name, address) VALUES (?, ?)"
        private const val UPDATE_LIBRARY_BY_ID = ""
        // In the event  are "deleted" erroneously, having a flag set instead of actually removing the entry allows
        // for quick reversal.
        private const val ARCHIVE_LIBRARY_BY_ID = ""
    }

    suspend fun create(library: NewLibrary): Long {
        val statement = connection.prepareStatement(INSERT_LIBRARY, Statement.RETURN_GENERATED_KEYS)
        statement.setString(1, library.name)
        statement.setString(2, library.address)
        try {
            statement.execute()
            val key = statement.generatedKeys
            if (key.next()) {
                return key.getLong(1)
            }
            return -1
        } catch (cause: SQLException) {
            throw DbElementInsertionException("Could not insert library: ${cause.message}")
        }
    }

    suspend fun read(id: Long): Library {
        val statement = connection.prepareStatement(SELECT_LIBRARY_BY_ID)
        statement.setLong(1, id)
        try {
            val resultSet = statement.executeQuery()
            if (resultSet.next() && resultSet.getBoolean("is_archived")) {
                return createLibraryFromResult(resultSet)
            } else {
                throw DbElementNotFoundException("Could not find collection item. resultSet: $resultSet")
            }
        } catch(cause: SQLException) {
            throw DbElementNotFoundException("Could not find collection item with id $id")
        }
    }

    suspend fun update(library: Library) {}

    suspend fun delete(id: String) {}

    fun createLibraryFromResult(resultSet: ResultSet): Library {
        try {
            val id = resultSet.getLong("id")
            val name = resultSet.getString("name")
            val address = resultSet.getString("address")
            val isArchived = resultSet.getBoolean("is_archived")
            return Library(id, name, address, isArchived)
        } catch (cause: NullPointerException) {
            throw DbElementInsertionException("${cause.message}\nresultSet = ${resultSet.metaData}")
        }
    }
}
