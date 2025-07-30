package codes.kalar

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection
import java.sql.DriverManager

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/patron") {
            if (call.request.queryParameters.isEmpty()) {
                call.respond(HttpStatusCode.BadRequest, "Invalid parameters")
            }
            else {
                call.respondText("Hello, ${call.request.queryParameters["patron"]}")
            }
        }

        post("/patron") {
            call.respondText("Patron is posted")
        }

        delete("/patron") {
            call.respondText("Do you have permissions?")
        }

        get("/libraries") {
            call.respondText("Libraries are neat!")
        }

        post("/libraries") {
            call.respondText("Library is posted")
        }

        delete("/libraries/{id}") {
            call.respondText("We hate to see you go!")
        }

        get("/items") {
            call.respondText("I'll search for that book!")
        }

        post("/items") {
            call.respondText("Somebody got back from Barnes & Noble!")
        }

        delete("/items/{id}") {
            call.respondText(":(")
        }

        get("/libraries/{libraryId}/items/{itemId}") {
            call.respondText("You asked for ${call.parameters["itemId"]} from ${call.parameters["libraryId"]}")
        }

        get("/authenticate") {
            call.respondText(System.getenv("JWT_DOMAIN") ?: "Ain't life a bitch?")
        }
    }
}
