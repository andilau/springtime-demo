package de.herrlau.demo

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SocialController() {

	@GetMapping("/user")
	fun user(@AuthenticationPrincipal principal: OAuth2User): Map<String, Any?> {
		if (principal.getAttribute<Any>("name") == null) return emptyMap()
		return mapOf("name" to principal.getAttribute<Any>("name"))
	}
}