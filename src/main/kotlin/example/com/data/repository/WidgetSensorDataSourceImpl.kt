package example.com.data.repository

import example.com.domain.model.*
import example.com.domain.repocitory.SensorDataSource
import org.litote.kmongo.combine
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class SensorDataSourceImpl(
    database: CoroutineDatabase
): SensorDataSource {

    private val listeners = mutableMapOf<Int, suspend (WidgetNotification) -> Unit>()
    private val sensors = database.getCollection<SensorData>()

    override fun addChangeListener(id: Int, listener: suspend (WidgetNotification) -> Unit) {
        listeners[id] = listener
    }
    override fun removeChangeListener(id: Int) {
        listeners.remove(id)
    }
    private suspend fun onChange(type: ChangeType, id: String, entity: SensorData? = null) {
        listeners.values.forEach {
            it.invoke(Notification(type, id, entity))
        }
    }

    override suspend fun getWidgetSensorById(id: String): SensorData? {
        return sensors.findOneById(id = SensorData::deviceId eq id)

    }

    override suspend fun saveWidgetSensor(widgetSensor: SensorData): Boolean {
        return run {
            val result = sensors.insertOne(document = widgetSensor)
            widgetSensor.id?.let { onChange(ChangeType.CREATE, it, widgetSensor) }
            result.wasAcknowledged()
        }
    }

    override suspend fun deleteWidgetSensor(id: String): Boolean {
        return sensors.deleteOne(filter = SensorData::id eq id).wasAcknowledged()
    }

    override suspend fun updateUserInfo(id: String, name: String): Boolean {
        val updates = combine(
            setValue(SensorData::name, name)
        )
        return sensors.updateOne(
            filter = SensorData::id eq id,
            update = updates
        ).wasAcknowledged()
    }
}