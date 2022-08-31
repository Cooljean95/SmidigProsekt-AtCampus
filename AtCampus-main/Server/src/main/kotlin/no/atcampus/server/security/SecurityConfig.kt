package no.atcampus.server.security

import no.atcampus.server.security.filter.CustomAuthenticationFilter
import no.atcampus.server.security.filter.CustomAuthorizationFilter
import no.atcampus.server.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Autowired private val passwordEncoder: PasswordEncoder,
    @Autowired private val userService: UserService
): WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder)
    }

    override fun configure(http: HttpSecurity) {
        val authenticationFilter = CustomAuthenticationFilter(authenticationManagerBean())
        authenticationFilter.setFilterProcessesUrl("/api/login")
        http
            .csrf().disable() // TODO: NEED TO ENABLE
            .sessionManagement().disable()
            .logout()
                .logoutUrl("/api/logout")
                .invalidateHttpSession(true)
                .logoutRequestMatcher(AntPathRequestMatcher("/logout")).permitAll()
            .and()
            .authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/refresh").permitAll()
                .antMatchers("/api/school/all").permitAll()
                .antMatchers("/api/school/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/api/user/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/api/group/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/api/post/**").hasAnyAuthority("USER", "ADMIN")
                .anyRequest().authenticated()
            .and()
            .addFilter(authenticationFilter)
            .addFilterBefore(CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

}