package example.com.domain.repocitory

import example.com.domain.model.User
import java.time.LocalDateTime

interface UserDataSource {
    suspend fun  getUserInfoById(id: String): User?
}