package example.com.data.repository

import example.com.domain.model.WidgetSensor
import example.com.domain.repocitory.WidgetSensorDataSource
import org.litote.kmongo.combine
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class WidgetSensorDataSourceImpl(
    database: CoroutineDatabase
): WidgetSensorDataSource {

    private val sensors = database.getCollection<WidgetSensor>()

    override suspend fun getWidgetSensorById(id: String): WidgetSensor? {
        return sensors.findOneById(id = WidgetSensor::idDevice eq id)
    }

    override suspend fun saveWidgetSensor(widgetSensor: WidgetSensor): Boolean {
        return run {
            val result = sensors.insertOne(document = widgetSensor)
            result.wasAcknowledged()
        }
    }

    override suspend fun deleteWidgetSensor(id: String): Boolean {
        return sensors.deleteOne(filter = WidgetSensor::id eq id).wasAcknowledged()
    }

    override suspend fun updateUserInfo(id: String, name: String): Boolean {
        val updates = combine(
            setValue(WidgetSensor::name, name)
        )
        return sensors.updateOne(
            filter = WidgetSensor::id eq id,
            update = updates
        ).wasAcknowledged()
    }
}