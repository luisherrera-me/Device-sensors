package example.com.plugins

import example.com.di.KoinModule

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.configureKoin(){
    install(Koin){
        modules(KoinModule)
    }
}