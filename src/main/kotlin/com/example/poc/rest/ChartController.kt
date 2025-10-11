package com.example.poc.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rest-api")
class ChartController {

    @GetMapping("/chart-data")
    fun chartData(): Map<String, Any> {
        return mapOf(
            "categories" to listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun"),
            "series" to listOf(
                mapOf(
                    "name" to "Series 1",
                    "data" to listOf(30, 40, 35, 50, 49, 60)
                ),
                mapOf(
                    "name" to "Series 2",
                    "data" to listOf(23, 12, 54, 61, 32, 40)
                )
            )
        )
    }
}
