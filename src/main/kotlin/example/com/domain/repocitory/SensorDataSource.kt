package example.com.domain.repocitory

import example.com.domain.model.SensorData
import example.com.domain.model.WidgetNotification


interface SensorDataSource {
    suspend fun getWidgetSensorById(id: String): SensorData?
    suspend fun saveWidgetSensor(widgetSensor: SensorData):Boolean
    suspend fun deleteWidgetSensor(id: String): Boolean
    suspend fun  updateUserInfo(
        id: String,
        name: String,
    ): Boolean

    fun addChangeListener(id: Int, listener: suspend (WidgetNotification) -> Unit)
    fun removeChangeListener(id: Int)


}