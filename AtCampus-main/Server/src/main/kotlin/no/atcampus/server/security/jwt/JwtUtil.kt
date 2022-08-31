package no.atcampus.server.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.security.core.userdetails.User
import java.util.*

object JwtUtil {

    private const val SECRET = "do_not_store_secrets_in_the_source_code"
    private const val REFRESH_SECRET = "sndandjwndjanjwdnwjdnajnduwjndioandwndsldnadwdwadaw"

    private val algorithm = Algorithm.HMAC256(SECRET)
    private val refreshAlgorithm = Algorithm.HMAC256(REFRESH_SECRET)

    fun createToken(user: User, issuer: String): String{
        return JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 10 * 1000))
            .withIssuer(issuer)
            .withClaim("authorities", user.authorities.map { it.authority })
            .sign(algorithm)
    }
    
    fun createRefreshToken(user: User, issuer: String): String{
        return JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000))
            .withIssuer(issuer)
            .withClaim("authorities", user.authorities.map { it.authority })
            .sign(refreshAlgorithm)
    }

    fun decodeToken(token: String): DecodedJWT {
        val jwtVerifier = JWT.require(algorithm).build()
        return jwtVerifier.verify(token)
    }
    
    fun decodeRefreshToken(refreshToken: String): DecodedJWT {
        val jwtVerifier = JWT.require(refreshAlgorithm).build()
        return jwtVerifier.verify(refreshToken)
    }

}