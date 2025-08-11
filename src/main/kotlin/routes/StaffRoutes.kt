package codes.kalar.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection

fun Application.configureStaffRoutes(dbConnection: Connection) {

    routing {
        get("/staff") {
            call.respondText("You better have sent a body")
        }

        get("/staff/{id}") {
            call.respondText(call.parameters["id"]!!)
        }

        post("/staff") {

        }

        patch("/staff") {

        }

        delete("/staff/{id}") {

        }
    }
}