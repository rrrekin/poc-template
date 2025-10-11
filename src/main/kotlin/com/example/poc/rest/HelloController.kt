package com.example.poc.rest

import com.example.poc.rest.dto.HelloResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rest-api")
class HelloController {

    @GetMapping("/hello")
    fun hello() = HelloResponse(
        message = "Don't Panic!",
        answer = 42,
        question = "What do you get if you multiply six by nine?"
    )
}
