package codes.kalar.service

import codes.kalar.model.CollectionItem
import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import java.sql.Connection
import java.sql.Statement

class CollectionItemService(private val connection: Connection) {
    var pageOfItems = mutableListOf<CollectionItem>()
    companion object {
        // TODO add create table statement
        private const val CREATE_COLLECTION_ITEMS_TABLE = "CREATE TABLE IF NOT EXISTS collection_items ("
        private const val SELECT_ITEM_BY_TITLE = "SELECT * FROM collection_items WHERE title = ? limit 10"
        private const val INSERT_ITEM = "INSERT INTO collection_items (name, population) VALUES (?, ?)"
        private const val UPDATE_ITEM = "UPDATE collection_items SET name = ?, population = ? WHERE id = ?"
        private const val DELETE_ITEM = "DELETE FROM collection_items WHERE id = ?"
    }

    init {
        val statement = connection.createStatement()
        val tables = connection.metaData.getTables(null, "public", "collection_items", arrayOf("TABLE"))
        if (!tables.next()) {
            statement.executeUpdate(CREATE_COLLECTION_ITEMS_TABLE)
        }
    }

    suspend fun create(collectionItem: CollectionItem) {}

    suspend fun read(collectionItem: CollectionItem) {}

    suspend fun update(collectionItem: CollectionItem) {}

    suspend fun delete(collectionItem: CollectionItem) {}
}
