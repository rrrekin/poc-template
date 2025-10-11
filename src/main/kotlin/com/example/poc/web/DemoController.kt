package com.example.poc.web

import com.example.poc.domain.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@Controller
@RequestMapping("/demo")
class DemoController(
    private val userService: UserService
) {

    @GetMapping
    fun demo(model: Model): String {
        model.addAttribute("pageTitle", "Tech Stack Demo")
        model.addAttribute("currentTime", LocalDateTime.now())
        model.addAttribute("activePage", "demo")
        return "demo"
    }

    @GetMapping("/widget")
    fun widget(model: Model): String {
        model.addAttribute("message", "Widget loaded at ${LocalDateTime.now()}")
        return "fragments/widget :: content"
    }

    @GetMapping("/users")
    fun getUsers(model: Model): String {
        val users = userService.getAllUsers()
        model.addAttribute("users", users)
        return "fragments/users-table :: content"
    }

    @PostMapping("/users")
    fun createUser(
        @RequestParam name: String,
        @RequestParam email: String,
        model: Model
    ): String {
        userService.createUser(name, email)
        val users = userService.getAllUsers()
        model.addAttribute("users", users)
        model.addAttribute("message", "User '$name' created successfully!")
        return "fragments/users-table :: content"
    }

    @DeleteMapping("/users/{id}")
    fun deleteUser(@PathVariable id: Int, model: Model): String {
        userService.deleteUser(id)
        val users = userService.getAllUsers()
        model.addAttribute("users", users)
        model.addAttribute("message", "User deleted successfully!")
        return "fragments/users-table :: content"
    }
}
