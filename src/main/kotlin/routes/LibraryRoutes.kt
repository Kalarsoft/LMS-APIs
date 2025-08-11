package codes.kalar.routes

import codes.kalar.model.Library
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection

fun Application.configureLibraryRoutes(dbConnection: Connection) {

    routing {
        get("/libraries") {
            call.respondText("Libraries are neat!")
        }

        get("/libraries/{libraryId}/items/{itemId}") {
            call.respondText("You asked for ${call.parameters["itemId"]} from ${call.parameters["libraryId"]}")
        }

        post("/libraries") {
            val library = call.receive<Library>()
            call.respondText("${library.name} is posted")
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