package de.herrlau.demo

import de.herrlau.demo.kx.uuid
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.servlet.function.RouterFunctions.route
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse.ok
import javax.persistence.Entity
import javax.persistence.Id

@Configuration
class MessageRoutes {

    @Bean
    fun handler(repository: MessageRepository) = MessageHandler(repository)

    @Bean
    fun all(handler: MessageHandler) = route()
        .GET("/messages", handler::all)
        .build()

    @Bean
    fun one(handler: MessageHandler) = route()
        .GET("/messages/{id}", handler::one)
        .build()

    @Bean
    fun post(handler: MessageHandler) = route()
        .POST("/messages", handler::post)
        .build()

}

class MessageHandler(private val repository: MessageRepository) {

    fun all(request: ServerRequest) = ok().body(repository.findAll(Sort.by("id", "text")))

    fun one(request: ServerRequest) = ok().body(repository.findById(request.pathVariable("id")))

    fun post(request: ServerRequest) =
        ok().body(repository.save(request.body(Message::class.java).apply { id = text.uuid() }))
}

interface MessageRepository : JpaRepository<Message, String?>

@Entity
class Message(
    @Id
    var id: String?,
    val text: String
)