package codes.kalar

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import kotlinx.serialization.json.Json

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080) {
        install(CORS) {
            anyHost()
            allowHeader(HttpHeaders.ContentType)
        }
    }.start(wait = true)
    EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    configureHTTP()
    configureSecurity()
    configureSerialization()
    configureDatabases()
    configureRouting()
}
