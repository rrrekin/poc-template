package com.example.poc.persistence

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.relational.core.dialect.AnsiDialect
import org.springframework.data.relational.core.dialect.Dialect
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import java.time.LocalDateTime
import javax.sql.DataSource

@Configuration
class JdbcConfig : AbstractJdbcConfiguration() {

    @Bean
    override fun jdbcDialect(operations: NamedParameterJdbcOperations): Dialect = AnsiDialect.INSTANCE

    @Bean
    override fun jdbcCustomConversions(): JdbcCustomConversions = JdbcCustomConversions(listOf(SqliteDateTimeConverter()))

    @Bean
    fun transactionManager(dataSource: DataSource): PlatformTransactionManager =
        DataSourceTransactionManager(dataSource).apply {
            isEnforceReadOnly = false
        }
}

@ReadingConverter
class SqliteDateTimeConverter : Converter<String, LocalDateTime> {
    override fun convert(source: String): LocalDateTime = when {
        source.contains('T') -> LocalDateTime.parse(source)  // ISO format from Java
        else -> LocalDateTime.parse(source.replace(' ', 'T')) // SQLite format
    }
}
