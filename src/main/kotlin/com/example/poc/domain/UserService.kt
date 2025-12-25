package com.example.poc.domain

import com.example.poc.persistence.User
import com.example.poc.persistence.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll().toList()

    fun getUserById(id: Int): User? = userRepository.findByIdOrNull(id)

    @Transactional
    fun createUser(name: String, email: String): User = userRepository.save(
        User(name = name, email = email, createdAt = LocalDateTime.now())
    )

    @Transactional
    fun updateUser(id: Int, name: String, email: String): Boolean {
        val user = userRepository.findByIdOrNull(id) ?: return false
        userRepository.save(user.copy(name = name, email = email))
        return true
    }

    @Transactional
    fun deleteUser(id: Int): Boolean {
        if (!userRepository.existsById(id)) return false
        userRepository.deleteById(id)
        return true
    }

    fun searchUsersByName(query: String): List<User> = userRepository.findByNameContaining(query)
}
