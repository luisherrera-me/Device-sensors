package example.com.domain.model

sealed class EndPoint (val path: String) {
    data object DataUser: EndPoint(path = "/api/v1/sensor")
}