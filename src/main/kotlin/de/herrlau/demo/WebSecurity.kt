package de.herrlau.demo

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.web.csrf.CookieCsrfTokenRepository

@Configuration
class WebSecurity : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http {
            cors { }
            csrf {
                //disable()
                csrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse()
            }
            authorizeRequests {
                authorize("/**", permitAll)
            }
            logout {
                logoutSuccessUrl = "/"
            }
            oauth2Login { }
        }
    }
}