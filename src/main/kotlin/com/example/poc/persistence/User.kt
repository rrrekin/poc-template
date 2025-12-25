package com.example.poc.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("users")
data class User(
    @Id val id: Int? = null,
    val name: String,
    val email: String,
    val createdAt: LocalDateTime? = null
)
