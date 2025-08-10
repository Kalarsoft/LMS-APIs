package codes.kalar.routes

import codes.kalar.exception.DbElementInsertionException
import codes.kalar.model.CollectionItem
import codes.kalar.service.CollectionItemService
import codes.kalar.exception.DbElementNotFoundException
import codes.kalar.model.NewCollectionItem
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection

fun Application.configureCollectionItemRoutes(dbConnection: Connection) {
    val itemService = CollectionItemService(dbConnection)

    routing {
        get("/items") {
            try {
                val input: Any
                val item: CollectionItem
                val items: MutableList<String>
                val offset: Int
                if (call.request.queryParameters["id"] != null) {
                    input = call.request.queryParameters["id"]!!.toLong()
                    item = itemService.readById(input)
                    call.respond(item)
                } else if (call.request.queryParameters["title"] != null) {
                    input = call.request.queryParameters["title"]!!.replace("-", " ")
                    offset = call.request.queryParameters["offset"]?.toInt() ?: 0
                    items = itemService.readByTitle(input, offset)
                    call.respond(items)
                }
                else {
                    throw IllegalArgumentException("query parameter required")
                }
            } catch(cause: DbElementNotFoundException) {
                call.respond(HttpStatusCode.NotFound, cause.message ?: "No element found in database.")
            } catch(cause: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, cause.message ?: "Bad Arguments")
            }
        }

        post("/items") {
            try {
                val item = call.receive<NewCollectionItem>()
                val id = itemService.create(item)
                call.respondText("Adding ${item.title} to database with the id of $id")
            } catch (e: DbElementInsertionException) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Bad Arguments")
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, "Bad Arguments. Must pass a valid CollectionItem object.")
            }

        }

        patch("/items") {
            try {
                val inputItem = call.receive<CollectionItem>()
                itemService.readById(inputItem.id)
                val result = itemService.update(inputItem)
                call.respondText("Updated ${inputItem.title} to database: $result")
            } catch (cause: DbElementNotFoundException) {
                log.error(cause.message)
                call.respond(HttpStatusCode.NotFound, "${cause.message}")
            }
            catch (cause: DbElementInsertionException) {
                log.error(cause.message)
                call.respond(HttpStatusCode.BadRequest, "${cause.message}")
            } catch (cause: ContentTransformationException) {
                log.error(cause.message)
                call.respond(HttpStatusCode.BadRequest, "${cause.message}")
            }
        }

        delete("/items/{id}") {
            try {
                val id = call.parameters["id"]!!.toLong()
                log.info("Deleting item with id=$id")
                itemService.delete(id)
                call.respondText(":(")
            } catch (cause: DbElementNotFoundException) {
                log.error(cause.message, cause)
            } catch (cause: NumberFormatException) {
                log.error(cause.message, cause)
            }
        }
    }
}