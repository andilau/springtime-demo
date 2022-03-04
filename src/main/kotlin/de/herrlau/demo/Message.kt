package de.herrlau.demo

import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@RestController
class MessageResource(private val repository: MessageRepository) {

    @GetMapping("/messages")
    fun all() = repository.findAll(Sort.by("id", "text"))

    @GetMapping("/messages/{id}")
    fun one(@PathVariable id: String) = repository.findById(id)

    @PostMapping("/messages")
    fun post(@RequestBody message: Message) {
        repository.save(message)
    }
}

interface MessageRepository : JpaRepository<Message?, String?>

@Entity
class Message(
    @Id
    @GeneratedValue
    val id: String?,
    var text: String
)