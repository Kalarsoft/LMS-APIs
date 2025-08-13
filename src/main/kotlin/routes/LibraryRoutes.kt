package codes.kalar.routes

import codes.kalar.exception.DbElementInsertionException
import codes.kalar.exception.DbElementNotFoundException
import codes.kalar.model.Library
import codes.kalar.model.NewLibrary
import codes.kalar.service.LibraryService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection
import java.sql.SQLException

fun Application.configureLibraryRoutes(dbConnection: Connection) {
    val libraryService = LibraryService(dbConnection)

    routing {
        get("/libraries") {
            try {
                val id = call.parameters["id"]?.toLong() ?: throw IllegalArgumentException("query parameter required")
                val library = libraryService.read(id)
                call.respond(HttpStatusCode.OK, library.toString())
            } catch (cause: DbElementNotFoundException) {
                call.respond(HttpStatusCode.BadRequest, cause.message ?: "Unable to find Library.")
            } catch (cause: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, cause.message ?: "Missing 'id' parameter.")
            }
        }

        get("/libraries/{libraryId}/items/{itemId}") {
            call.respondText("You asked for ${call.parameters["itemId"]} from ${call.parameters["libraryId"]}")
        }

        post("/libraries") {
            val library = call.receive<NewLibrary>()
            try {
                val id = libraryService.create(library)
                call.respondText("${library.name} is posted with the ID: $id")
            } catch (cause: DbElementInsertionException) {
                call.respond(HttpStatusCode.BadRequest, cause.message ?: "Unable to insert Library.")
            }
        }

        patch("/libraries") {
            val library = call.receive<Library>()
            call.respondText("${library.name} is patched")
        }

        delete("/libraries") {
            call.respondText("We hate to see you go!")
        }
    }

}