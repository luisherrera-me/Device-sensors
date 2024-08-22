package example.com.domain.repocitory

import example.com.domain.model.User

interface UserDataSource {
    suspend fun  getUserInfoById(id: String): User?
}