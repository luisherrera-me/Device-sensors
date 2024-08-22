package example.com

import example.com.plugins.*
import example.com.services.JwtService
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val jwtService = JwtService(this)
    configureKoin()
    configureSerialization()
    configureSecurity(jwtService)
    configureRouting(jwtService)
}
