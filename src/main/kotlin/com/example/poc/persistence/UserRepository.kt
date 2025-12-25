package com.example.poc.persistence

import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int> {
    fun findByNameContaining(name: String): List<User>
}
