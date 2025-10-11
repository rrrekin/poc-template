package com.example.poc.persistence

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Users : Table("users") {
    val id = integer("id").autoIncrement()
    val name = text("name")
    val email = text("email")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)
}

data class User(
    val id: Int? = null,
    val name: String,
    val email: String,
    val createdAt: LocalDateTime? = null
)
