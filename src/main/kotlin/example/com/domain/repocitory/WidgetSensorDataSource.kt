package example.com.domain.repocitory

import example.com.domain.model.WidgetSensor
import java.time.LocalDateTime

interface WidgetSensorDataSource {
    suspend fun getWidgetSensorById(id: String): WidgetSensor?
    suspend fun saveWidgetSensor(widgetSensor: WidgetSensor):Boolean
    suspend fun deleteWidgetSensor(id: String): Boolean
    suspend fun  updateUserInfo(
        id: String,
        name: String,
    ): Boolean
}