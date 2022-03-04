package de.herrlau.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.*

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

@RestController
class SocialController() {

	@GetMapping("/user")
	fun user(@AuthenticationPrincipal principal: OAuth2User): Map<String, Any?> {
		if (principal.getAttribute<Any>("name") == null) return emptyMap()
		return mapOf("name" to principal.getAttribute<Any>("name"))
	}
}