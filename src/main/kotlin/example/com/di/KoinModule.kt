package example.com.di

import example.com.domain.model.WidgetSensor
import example.com.domain.repocitory.WidgetSensorDataSource
import example.com.utils.Constants.DATABASE_NAME
import io.ktor.http.auth.*
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val KoinModule = module {
    single {
    KMongo.createClient(System.getenv("MONGO_URI"))
        .coroutine
        .getDatabase(DATABASE_NAME)
    }

    single<WidgetSensor>{
        WidgetSensorDataSourceImpl(get())
    }

}