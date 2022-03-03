package de.herrlau.demo

import de.herrlau.demo.kx.uuid
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
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

// @RestController("/messages")
class MessageResource(private val service: MessageService) {

	@GetMapping
	fun index(): List<Message> = service.findMessages()

	@GetMapping("{id}")
	fun index(@PathVariable id: String): List<Message> = service.findMessageById(id)

	@PostMapping
	fun post(@RequestBody message: Message) {
		service.post(message)
	}
}

@Service
class MessageService(val db: JdbcTemplate) {

	fun findMessages(): List<Message> = db.query("select * from messages") { rs, _ ->
		Message(rs.getString("id"), rs.getString("text"))
	}

	fun findMessageById(id: String): List<Message> = db.query("select * from messages where id = ?", id) { rs, _ ->
		Message(rs.getString("id"), rs.getString("text"))
	}

	fun post(message: Message) {
		db.update("insert into messages values (?,?)", message.id ?: message.text.uuid(), message.text)
	}
}

data class Message(val id: String?, val text: String)