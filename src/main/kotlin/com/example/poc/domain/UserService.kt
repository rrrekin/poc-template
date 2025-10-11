package com.example.poc.domain

import com.example.poc.persistence.User
import com.example.poc.persistence.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class UserService {

    fun getAllUsers(): List<User> = transaction {
        Users.selectAll().map { row ->
            User(
                id = row[Users.id],
                name = row[Users.name],
                email = row[Users.email],
                createdAt = row[Users.createdAt]
            )
        }
    }

    fun getUserById(id: Int): User? = transaction {
        Users.select(Users.id eq id)
            .map { row ->
                User(
                    id = row[Users.id],
                    name = row[Users.name],
                    email = row[Users.email],
                    createdAt = row[Users.createdAt]
                )
            }
            .singleOrNull()
    }

    fun createUser(name: String, email: String): User = transaction {
        val id = Users.insert {
            it[Users.name] = name
            it[Users.email] = email
        } get Users.id

        User(
            id = id,
            name = name,
            email = email
        )
    }

    fun updateUser(id: Int, name: String, email: String): Boolean = transaction {
        Users.update({ Users.id eq id }) {
            it[Users.name] = name
            it[Users.email] = email
        } > 0
    }

    fun deleteUser(id: Int): Boolean = transaction {
        Users.deleteWhere { Users.id eq id } > 0
    }

    fun searchUsersByName(query: String): List<User> = transaction {
        Users.select(Users.name like "%$query%")
            .map { row ->
                User(
                    id = row[Users.id],
                    name = row[Users.name],
                    email = row[Users.email],
                    createdAt = row[Users.createdAt]
                )
            }
    }
}
