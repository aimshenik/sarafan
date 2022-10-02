package com.imshenik.sarafan.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.imshenik.sarafan.exceptions.NotFoudException
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

@RestController
@RequestMapping("message")
class MessageController {
    private var counter = 4
    private var messages: MutableList<MutableMap<String, String>> = mutableListOf(
        mutableMapOf("id" to "1", "text" to "First message"),
        mutableMapOf("id" to "2", "text" to "Second message"),
        mutableMapOf("id" to "3", "text" to "Third message"),
    )

    @GetMapping
    fun list(): List<Map<String, String>> {
        return messages
    }

    @GetMapping("{id}")
    fun getOne(@PathVariable id: String): Map<String, String> {
        return getMessage(id)

    }

    private fun getMessage(id: String) = messages
        .filter { it.get("id") == id }
        .firstOrNull() ?: throw NotFoudException()

    @PostMapping
    fun create(@RequestBody message: MutableMap<String, String>): MutableMap<String, String> {
        message += Pair("id", counter++.toString())
        messages += message
        println("CREATE : ${messages.toString()}")
        return message
    }

    @PutMapping("{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody message: MutableMap<String, String>
    ): MutableMap<String, String>? {
        val messageFromDB = getMessage(id)
        messageFromDB?.putAll(message)
        messageFromDB?.put("id", id)
        return messageFromDB
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: String) {
        val message = getMessage(id)
        messages -= message
    }
}