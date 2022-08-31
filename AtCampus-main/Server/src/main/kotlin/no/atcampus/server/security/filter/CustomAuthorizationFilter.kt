package no.atcampus.server.security.filter

import com.auth0.jwt.exceptions.TokenExpiredException
import com.auth0.jwt.interfaces.DecodedJWT
import no.atcampus.server.security.jwt.JwtUtil
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthorizationFilter: OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val bearer: String? = request.getHeader("Authorization");
        
        when{
            bearer.isNullOrEmpty() -> filterChain.doFilter(request, response)
            request.servletPath.contains("/api/login") -> filterChain.doFilter(request, response)
            request.servletPath.contains("/api/register") -> filterChain.doFilter(request, response)
            request.servletPath.contains("/api/refresh") -> filterChain.doFilter(request, response)
            request.servletPath.contains("/api/user/**") -> filterChain.doFilter(request, response)
            request.servletPath.contains("/api/groups/**") -> filterChain.doFilter(request, response)
            request.servletPath.contains("/api/post/**") -> filterChain.doFilter(request, response)
            else -> {
                val token = bearer.substring(7)
                val decodedJwt: DecodedJWT
                
                try {
                    decodedJwt = JwtUtil.decodeToken(token)
                } catch (e: TokenExpiredException) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token expired")
                    
                    return
                }
                
                val email = decodedJwt.subject
                val authority =
                    decodedJwt.getClaim("authorities").asList(String::class.java).map { SimpleGrantedAuthority(it) }
                val authenticationToken = UsernamePasswordAuthenticationToken(email, null, authority)
                SecurityContextHolder.getContext().authentication = authenticationToken
                filterChain.doFilter(request, response)
            }
        }
    }
}