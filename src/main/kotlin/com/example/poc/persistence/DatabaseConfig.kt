package com.example.poc.persistence

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

@Configuration
class DatabaseConfig {

    private val logger = LoggerFactory.getLogger(DatabaseConfig::class.java)

    @EventListener(ApplicationReadyEvent::class)
    fun initializeDatabase() {
        logger.info("Initializing database schema...")

        transaction {
            // Create tables if they don't exist
            SchemaUtils.create(Users)

            // Optional: Add sample data for demo purposes
            if (Users.selectAll().empty()) {
                logger.info("Seeding database with sample data...")

                Users.insert {
                    it[name] = "Ford Prefect"
                    it[email] = "ford.prefect@hitchhikers.guide"
                }

                Users.insert {
                    it[name] = "Arthur Dent"
                    it[email] = "arthur.dent@earth.com"
                }

                Users.insert {
                    it[name] = "Zaphod Beeblebrox"
                    it[email] = "zaphod@heartofgold.ship"
                }

                logger.info("Sample data seeded successfully")
            }
        }

        logger.info("Database initialization complete")
    }
}
