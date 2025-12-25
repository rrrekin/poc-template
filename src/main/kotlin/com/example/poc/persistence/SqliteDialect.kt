package com.example.poc.persistence

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
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
    override fun jdbcCustomConversions(): JdbcCustomConversions = JdbcCustomConversions(
        listOf(SqliteDateTimeReadConverter(), SqliteDateTimeWriteConverter())
    )

    // SQLite doesn't support Connection.setReadOnly() after connection is established.
    // This causes SQLException when Spring tries to set read-only mode for @Transactional(readOnly=true).
    // Disabling enforceReadOnly is required workaround for SQLite. For production with PostgreSQL/MySQL, remove this.
    @Bean
    fun transactionManager(dataSource: DataSource): PlatformTransactionManager =
        DataSourceTransactionManager(dataSource).apply { isEnforceReadOnly = false }
}

@ReadingConverter
class SqliteDateTimeReadConverter : Converter<String, LocalDateTime> {
    override fun convert(source: String): LocalDateTime = when {
        source.contains('T') -> LocalDateTime.parse(source)
        else -> LocalDateTime.parse(source.replace(' ', 'T'))
    }
}

@WritingConverter
class SqliteDateTimeWriteConverter : Converter<LocalDateTime, String> {
    override fun convert(source: LocalDateTime): String = source.toString().replace('T', ' ')
}
