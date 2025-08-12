package codes.kalar

import codes.kalar.routes.configureCollectionItemRoutes
import codes.kalar.routes.configureLibraryRoutes
import codes.kalar.routes.configurePatronRoutes
import codes.kalar.routes.configureStaffRoutes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.swagger.*
import java.sql.Connection

fun Application.configureRouting() {
    val dbConnection: Connection = connectToPostgres()

    configureCollectionItemRoutes(dbConnection)
    configurePatronRoutes(dbConnection)
    configureLibraryRoutes(dbConnection)
    configureStaffRoutes(dbConnection)

    routing {
        get("/") {
            call.respondText("Hello World!", status = HttpStatusCode.OK)
        }

        get("/authenticate") {
            call.respondText(System.getenv("JWT_DOMAIN") ?: "You seem to be missing something")
        }

        swaggerUI(path = "swagger", swaggerFile = "src/main/resources/openapi/documentation.yaml") {
            version="5.27.1"
        }
    }
}
