package example.com.utils

import kotlinx.serialization.json.Json

object JsonMapper {

    val defaultMapper = Json {
        prettyPrint = true
    }

}