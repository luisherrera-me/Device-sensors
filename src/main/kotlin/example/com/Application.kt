package example.com


import example.com.plugins.*
import example.com.services.JwtService
import example.com.utils.JsonMapper
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.websocket.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val jwtService = JwtService(this)
    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(JsonMapper.defaultMapper)
    }
    configureKoin()
    configureSerialization()
    configureSecurity(jwtService)
    configureRouting(jwtService)
}
