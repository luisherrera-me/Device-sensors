package example.com.plugins

import example.com.domain.model.EndPoint
import example.com.domain.repocitory.UserDataSource
import example.com.domain.repocitory.WidgetSensorDataSource
import example.com.routes.postDataSensorRoute
import example.com.services.JwtService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent

fun Application.configureRouting(
    jwtService: JwtService,
) {
    routing {
        val widgetSensorDataSource: WidgetSensorDataSource by KoinJavaComponent.inject(WidgetSensorDataSource::class.java)
        val userDataSource: UserDataSource by KoinJavaComponent.inject(UserDataSource::class.java)

        route(EndPoint.DataUser.path) {
            postDataSensorRoute( widgetSensorDataSource, jwtService, userDataSource )

        }
    }
}
