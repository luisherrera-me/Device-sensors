package example.com.routes

import example.com.domain.model.*
import example.com.domain.repocitory.UserDataSource
import example.com.domain.repocitory.SensorDataSource
import example.com.services.JwtService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import java.lang.Exception
import java.time.LocalDateTime
import java.util.*


fun Route.postDataSensorRoute(
    widgetSensorDataSource: SensorDataSource,
    jwtService: JwtService,
    userDataSource: UserDataSource
){
    authenticate("another-auth"){
        post (){
            val widgetSensor = call.receive<SensorData>()
            val device = userDataSource.getUserInfoById(widgetSensor.deviceId)
            val principalID = extractPrincipalUsername(call)
            if (device != null){
                userDataSource.getUserInfoById(widgetSensor.deviceId)

                try {
                    if (principalID == device.id)
                    saveUserToDatabase(
                        widgetSensor,
                        jwtService,
                        userDataSource,
                        widgetSensorDataSource
                    )else{
                        call.respond(
                            status = HttpStatusCode.Forbidden,
                            message = ApiResponseError(
                                statusCode = 403,
                                message = "No autorizado"
                            )
                        )
                    }
                } catch (e: Exception) {
                    call.respond(
                        status = HttpStatusCode.Forbidden,
                        message = ApiResponseError(
                            statusCode = 403,
                            message = "Error del servidor ${e.message}"
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

private fun SensorData.toModel(): SensorData =

    SensorData(
        id = UUID.randomUUID().toString(),
        name = this.name,
        fact = this.fact,
        date = LocalDateTime.now(),
        location = this.location,
        deviceId = this.deviceId

    )

private suspend fun PipelineContext<Unit, ApplicationCall>.saveUserToDatabase(
    sensorDataRequest: SensorData,
    jwtService: JwtService,
    userDataSource: UserDataSource,
    widgetSensorDataSource: SensorDataSource
) {
    val widgetSensor = sensorDataRequest.toModel()
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

private fun extractPrincipalUsername(call: ApplicationCall): String? =
    call.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("UserId")
        ?.asString()