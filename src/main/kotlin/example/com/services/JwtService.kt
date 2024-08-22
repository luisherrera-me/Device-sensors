package example.com.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import example.com.domain.model.User
import example.com.domain.repocitory.UserDataSource
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*
import kotlinx.coroutines.runBlocking
import org.koin.java.KoinJavaComponent

class JwtService (
    private val application: Application
){
    private val userDataSource: UserDataSource by KoinJavaComponent.inject(UserDataSource::class.java)

    private val secret = getConfigProperty("jwt.secret")
    private val issuer = getConfigProperty("jwt.issuer")
    private val audience = getConfigProperty("jwt.audience")

    val realm = getConfigProperty("jwt.realm")

    val jwtVerifier: JWTVerifier =
        JWT
            .require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()


    fun customValidator(
        credential: JWTCredential,
    ): JWTPrincipal? {
        val emailAddress: String? = extractEmailAddress(credential)
        val foundUser: User? = emailAddress?.let{ userId ->
            runBlocking {
                userDataSource.getUserInfoById(userId)
            }
        }

        return foundUser?.let {
            if (audienceMatches(credential))
                JWTPrincipal(credential.payload)
            else
                null
        }
    }

    private fun audienceMatches(
        credential: JWTCredential,
    ): Boolean =
        credential.payload.audience.contains(audience)

    private fun getConfigProperty(path: String) =
        application.environment.config.property(path).getString()

    private fun extractEmailAddress(credential: JWTCredential): String? =
        credential.payload.getClaim("UserId").asString()
}