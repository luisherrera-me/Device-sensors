package example.com.di

import example.com.data.repository.UserDataSourceImpl
import example.com.data.repository.SensorDataSourceImpl
import example.com.domain.repocitory.SensorDataSource
import example.com.domain.repocitory.UserDataSource
import example.com.utils.Constants.DATABASE_NAME
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val KoinModule = module {
    single {
    KMongo.createClient(System.getenv("MONGODB_URI"))
        .coroutine
        .getDatabase(DATABASE_NAME)
    }

    single<SensorDataSource>{
        SensorDataSourceImpl(get())
    }

    single<UserDataSource>{
        UserDataSourceImpl(get())
    }

}