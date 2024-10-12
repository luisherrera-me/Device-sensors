package example.com.routes

import example.com.domain.repocitory.SensorDataSource
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
fun Route.UpdatesSocket(sensorDS: SensorDataSource) {

    webSocket("/updates") {
        try {
            sensorDS.addChangeListener(this.hashCode()) {
                sendSerialized(it)
            }
            for (frame in incoming) {
                if (frame.frameType == FrameType.CLOSE) {
                    break
                } else if (frame is Frame.Text) {
                    call.application.environment.log.info("Mensaje de websocket recibido: {}", frame.readText())
                }
            }
        } finally {
            sensorDS.removeChangeListener(this.hashCode())
        }
    }

}