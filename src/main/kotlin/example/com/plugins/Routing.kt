package example.com.plugins

import example.com.domain.model.EndPoint
import example.com.domain.repocitory.UserDataSource
import example.com.domain.repocitory.SensorDataSource
import example.com.routes.UpdatesSocket
import example.com.routes.postDataSensorRoute
import example.com.services.JwtService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent

fun Application.configureRouting(
    jwtService: JwtService,
) {
    routing {
        val widgetSensorDataSource: SensorDataSource by KoinJavaComponent.inject(SensorDataSource::class.java)
        val userDataSource: UserDataSource by KoinJavaComponent.inject(UserDataSource::class.java)

        route(EndPoint.DataUser.path) {
            postDataSensorRoute( widgetSensorDataSource, jwtService, userDataSource )
            UpdatesSocket(widgetSensorDataSource)
        }
    }
}
