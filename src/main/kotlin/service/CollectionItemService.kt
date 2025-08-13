package codes.kalar.service

import codes.kalar.exception.DbElementInsertionException
import codes.kalar.exception.DbElementNotFoundException
import codes.kalar.model.CollectionItem
import codes.kalar.model.NewCollectionItem
import kotlinx.serialization.json.Json
import java.sql.*

class CollectionItemService(private val connection: Connection) {

    companion object {
        private const val SELECT_ITEM_BY_TITLE = "SELECT * FROM collection_item WHERE levenshtein(title, ?) <= 5 LIMIT 25 OFFSET ?"
        private const val SELECT_ITEM_BY_ID = "SELECT * FROM collection_item WHERE id = ?"
        private const val INSERT_ITEM = "INSERT INTO collection_item (title, author, publisher, publishing_date, " +
                "loc_number, dewey_decimal_number, isbn, sort_title, format, language, page_count, categories, " +
                "description, price_in_cents, cover_image_uri, is_checked_in, is_archived, is_lost, lost_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        private const val UPDATE_ITEM_BY_ID = "UPDATE collection_item SET title = ?, author = ?, publisher = ?, " +
                "publishing_date = ?, loc_number = ?, dewey_decimal_number = ?, isbn = ?, sort_title = ?, format = ?, " +
                "language = ?, page_count = ?, categories = ?, description = ?, price_in_cents = ?, cover_image_uri = ?, " +
                "is_checked_in = ?, is_archived = ?, is_lost = ?, lost_date = ? WHERE id = ?"
        // In the event books are "deleted" erroneously, having a flag set instead of actually removing the entry allows
        // for quick reversal.
        private const val ARCHIVE_ITEM_BY_ID = "UPDATE collection_item SET is_archived = true WHERE id = ?"
    }

    fun create(newCollectionItem: NewCollectionItem): Long {
        val statement = connection.prepareStatement(INSERT_ITEM,
            Statement.RETURN_GENERATED_KEYS)
        statement.setString(1, newCollectionItem.title)
        statement.setString(2, newCollectionItem.author)
        statement.setString(3, newCollectionItem.publisher)
        statement.setString(4, newCollectionItem.publishingDate)
        statement.setString(5, newCollectionItem.locNumber)
        statement.setString(6, newCollectionItem.deweyDecimalNumber)
        statement.setLong(7, newCollectionItem.isbn)
        statement.setString(8, newCollectionItem.sortTitle)
        statement.setString(9, newCollectionItem.format)
        statement.setString(10, newCollectionItem.language)
        statement.setInt(11, newCollectionItem.pageCount)
        statement.setString(12, newCollectionItem.categories)
        statement.setString(13, newCollectionItem.description)
        statement.setInt(14, newCollectionItem.priceInCents)
        statement.setString(15, newCollectionItem.coverImageUri)
        statement.setBoolean(16, newCollectionItem.isCheckedIn)
        statement.setBoolean(17, newCollectionItem.isArchived)
        statement.setBoolean(18, newCollectionItem.isLost)
        statement.setDate(19, Date.valueOf(newCollectionItem.lostDate))
        try{
            statement.execute()
            val key = statement.generatedKeys
            if (key.next()) {
                return key.getLong(1)
            }
            return -1
        } catch (cause: SQLException) {
            throw DbElementInsertionException("Couldn't insert item " +
                    "${newCollectionItem.title} into database. ${cause.message}")
        }
    }

    fun readByTitle(inputTitle: String, offset: Int = 0): MutableList<String> {
        val itemList = mutableListOf<String>()
        // FUZZY SEARCH!!!!
        val statement = connection.prepareStatement(SELECT_ITEM_BY_TITLE)
        statement.setString(1, inputTitle)
        statement.setInt(2, offset * 25)
        val resultSet = statement.executeQuery()
        if (!resultSet.next()) {
            throw DbElementNotFoundException("Could not find collection item.")
        }
        while (resultSet.next()) {
            // Only return non-archived books
            if (!resultSet.getBoolean("is_archived")) {
                val collectionItem = createItemFromResult(resultSet)
                itemList.addLast(Json.encodeToString(collectionItem))
            }
        }
        return itemList
    }

    fun readById(id: Long): CollectionItem {
        val statement = connection.prepareStatement(SELECT_ITEM_BY_ID)
        statement.setLong(1, id)
        try {
            val resultSet = statement.executeQuery()
            // Only return non-archived books
            if (resultSet.next() && !resultSet.getBoolean("is_archived")) {
                return createItemFromResult(resultSet)
            } else {
                throw DbElementNotFoundException("Could not find collection item. resultSet: $resultSet")
            }
        } catch(cause: SQLException) {
            throw DbElementNotFoundException("Could not find collection item with id $id")
        }
    }

    fun update(collectionItem: CollectionItem): Boolean {
        val statement = connection.prepareStatement(UPDATE_ITEM_BY_ID)
        try {
            statement.setString(1, collectionItem.title)
            statement.setString(2, collectionItem.author)
            statement.setString(3, collectionItem.publisher)
            statement.setString(4, collectionItem.publishingDate)
            statement.setString(5, collectionItem.locNumber)
            statement.setString(6, collectionItem.deweyDecimalNumber)
            statement.setLong(7, collectionItem.isbn)
            statement.setString(8, collectionItem.sortTitle)
            statement.setString(9, collectionItem.format)
            statement.setString(10, collectionItem.language)
            statement.setInt(11, collectionItem.pageCount)
            statement.setString(12, collectionItem.categories)
            statement.setString(13, collectionItem.description)
            statement.setInt(14, collectionItem.priceInCents)
            statement.setString(15, collectionItem.coverImageUri)
            statement.setBoolean(16, collectionItem.isCheckedIn)
            statement.setBoolean(17, collectionItem.isArchived)
            statement.setBoolean(18, collectionItem.isLost)
            statement.setDate(19, Date.valueOf(collectionItem.lostDate))
            statement.setLong(20, collectionItem.id)
            return statement.execute()
        } catch (e: SQLException) {
            throw DbElementInsertionException("${e.message}\ncollectionItem: $collectionItem\n statement: $statement\n ", e)
        } catch (e: IllegalArgumentException) {
            throw DbElementInsertionException("${e.message}\ncollectionItem: $collectionItem\n statement: $statement\n ", e)
        }
    }

    fun delete(id: Long) {
        val statement = connection.prepareStatement(ARCHIVE_ITEM_BY_ID)
        try {
            statement.setLong(1, id)
            statement.execute()
        } catch (e: SQLException) {
            throw DbElementNotFoundException("Could not find collection item with id $id")
        }
    }
}

fun createItemFromResult(resultSet: ResultSet): CollectionItem {
    try {
        val id = resultSet.getLong("id")
        val title = resultSet.getString("title")
        val author = resultSet.getString("author")
        val publisher = resultSet.getString("publisher")
        val publishingDate = resultSet.getDate("publishing_date")
        val locNumber = resultSet.getString("loc_number")
        val deweyDecimalNumber = resultSet.getString("dewey_decimal_number")
        val isbn = resultSet.getLong("isbn")
        val sortTitle = resultSet.getString("sort_title")
        val format = resultSet.getString("format")
        val language = resultSet.getString("language")
        val pageCount = resultSet.getInt("page_count")
        val categories = resultSet.getString("categories")
        val description = resultSet.getString("description")
        val priceInCents = resultSet.getInt("price_in_cents")
        val coverImageUri = resultSet.getString("cover_image_uri") ?: ""
        val isCheckedIn = resultSet.getBoolean("is_checked_in")
        val isArchived = resultSet.getBoolean("is_archived")
        val isLost = resultSet.getBoolean("is_lost")
        val lostDate = resultSet.getDate("lost_date")
        return CollectionItem(
            id = id,
            title = title,
            author = author,
            publisher = publisher,
            publishingDate = publishingDate.toString(),
            locNumber = locNumber,
            deweyDecimalNumber = deweyDecimalNumber,
            isbn = isbn,
            sortTitle = sortTitle,
            format = format,
            language = language,
            pageCount = pageCount,
            categories = categories,
            description = description,
            priceInCents = priceInCents,
            coverImageUri = coverImageUri,
            isCheckedIn = isCheckedIn,
            isArchived = isArchived,
            isLost = isLost,
            lostDate = lostDate.toString()
        )
    } catch (cause: NullPointerException) {
        throw DbElementInsertionException("${cause.message}\nresultSet = ${resultSet.metaData}")
    }
}
