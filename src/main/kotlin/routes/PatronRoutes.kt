package codes.kalar.routes

import codes.kalar.model.Patron
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection

fun Application.configurePatronRoutes(dbConnection: Connection) {

    routing {
        get("/patron") {
            if (call.request.queryParameters["patron"] == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid parameters")
            }
            else {
                call.respondText("Hello, ${call.request.queryParameters["patron"]}")
            }
        }

        post("/patron") {
            val patron = call.receive<Patron>()
            call.respondText("${patron.name} is posted")
        }

        patch("/patron") {
            val patron = call.receive<Patron>()
            call.respondText("${patron.name} is patched")
        }

        delete("/patron") {
            if (call.request.queryParameters["id"] == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid parameters")
            } else {
                call.respondText("Do you have permissions?")
            }
        }
    }
}