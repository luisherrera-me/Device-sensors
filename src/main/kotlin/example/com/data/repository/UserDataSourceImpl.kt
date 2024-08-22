package example.com.data.repository


import example.com.domain.model.User
import example.com.domain.repocitory.UserDataSource
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class UserDataSourceImpl(
    database: CoroutineDatabase
): UserDataSource {

    private  val users = database.getCollection<User>()

    override suspend fun getUserInfoById(id: String): User? {
        return users.findOne(filter = User::id eq id)
    }

}