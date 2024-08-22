package example.com.routes

import example.com.domain.model.ApiResponseError
import example.com.domain.model.WidgetSensor
import example.com.domain.repocitory.UserDataSource
import example.com.domain.repocitory.WidgetSensorDataSource
import example.com.services.JwtService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import java.lang.Exception
import java.time.LocalDateTime
import java.util.*


fun Route.postDataSensorRoute(
    widgetSensorDataSource: WidgetSensorDataSource,
    jwtService: JwtService,
    userDataSource: UserDataSource
){
    authenticate("another-auth"){
        post (){
            val widgetSensor = call.receive<WidgetSensor>()
            val device = userDataSource.getUserInfoById(widgetSensor.idDevice)
            if (device != null){
                userDataSource.getUserInfoById(widgetSensor.idDevice)

                try {
                    saveUserToDatabase(
                        widgetSensor,
                        jwtService,
                        userDataSource,
                        widgetSensorDataSource
                    )
                } catch (e: Exception) {
                    call.respond(
                        status = HttpStatusCode.Forbidden,
                        message = ApiResponseError(
                            statusCode = 403,
                            message = "Error del servidor"
                        )
                    )
                }
            }else{
                call.respond(
                    status = HttpStatusCode.Forbidden,
                    message = ApiResponseError(
                        statusCode = 403,
                        message = "Dispocitivo no existe"
                    )
                )
            }
        }
    }
}

private fun WidgetSensor.toModel(): WidgetSensor =

    WidgetSensor(
        id = UUID.randomUUID().toString(),
        name = this.name,
        fact = this.fact,
        date = LocalDateTime.now(),
        idDevice = this.idDevice,
        location = this.location

    )

private suspend fun PipelineContext<Unit, ApplicationCall>.saveUserToDatabase(
    widgetSensorRequest: WidgetSensor,
    jwtService: JwtService,
    userDataSource: UserDataSource,
    widgetSensorDataSource: WidgetSensorDataSource
) {
    val widgetSensor = widgetSensorRequest.toModel()
    val response = widgetSensorDataSource.saveWidgetSensor(widgetSensor)

    return if (response) {
        call.respond(
            status = HttpStatusCode.OK,
            message = ApiResponseError(
                statusCode = 200,
                message = "true"
            )
        )
    } else {
        // Si el usuario ya existe
        call.respond(
            status = HttpStatusCode.Forbidden,
            message = ApiResponseError(
                statusCode = 403,
                message = "Email ya esta en uso."
            )
        )
    }
}