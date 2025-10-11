# POC Template Project

## Technology Stack

**Backend:**
- **Spring Boot 3.5.6** - Application framework
- **Kotlin 2.2.20** - Programming language
- **JVM 21** - Runtime (LTS)
- **Exposed 0.61.0** - Kotlin SQL DSL for database operations
- **SQLite** - Embedded database (file: `data/template.db`)

**Frontend:**
- **Thymeleaf** - Server-side HTML templating
- **HTMX 2.0.7** - Dynamic HTML updates without page reloads
- **Alpine.js 3.15.0** - Client-side reactivity and UI state
- **Bulma 1.0.4** - CSS framework (compact design)
- **ApexCharts** - Data visualization and charts

**AI & File Processing:**
- **OpenAI Java SDK 4.3.0** - OpenAI API integration
- **ONNX Runtime 1.23.1** - Local embeddings generation
- **Apache POI 5.4.1** - Excel file handling
- **Apache Commons CSV 1.14.1** - CSV parsing

**Development:**
- **Spring Boot DevTools** - Hot reload
- **Gradle Kotlin DSL** - Build system

## Project Structure

```
src/main/kotlin/com/example/poc/  # Main application entry point
├── web/                    # Web page controllers (Thymeleaf)
├── rest/                   # REST API controllers (JSON)
│   └── dto/                # DTOs for REST API
├── domain/                 # Business logic services
└── persistence/            # Database models & configuration

src/main/resources/
├── templates/              # Thymeleaf HTML templates
│   ├── layout.html        # Base layout
│   └── fragments/         # Reusable HTML fragments
├── static/                # CSS, JS, images
└── application.yml        # Configuration
```

## Standard Commands

```bash
# Build project
./gradlew build

# Run application
./gradlew bootRun

# Clean build (when compilation issues occur)
./gradlew clean build --no-build-cache

# Run tests
./gradlew test

# Create JAR
./gradlew bootJar

# Check for dependency updates (via Version Catalog)
./gradlew versionCatalogUpdate  # Auto-updates gradle/libs.versions.toml

# Legacy: Check for dependency updates (via Versions Plugin)
./gradlew dependencyUpdates
```

## Quick Development Pattern

1. Define database table in `persistence/` using Exposed DSL
2. Create service in `domain/` with business logic
3. Create controller in `web/` (pages) or `rest/` (API)
4. Create Thymeleaf template in `resources/templates/`
5. Use HTMX for dynamic updates, Alpine.js for UI state
6. Access at http://localhost:8080

## Configuration

- **Port:** 8080 (configurable in `application.yml`)
- **Database:** `data/template.db` (auto-created)
- **OpenAI API Key:** Set `OPENAI_API_KEY` environment variable
- **DevTools:** Enabled (auto-restart on code changes)

## Notes

- No authentication configured (POC only)
- All frontend libraries loaded via WebJars (no npm/build step)
- SQLite for development; switch to PostgreSQL for production
- Server-side rendering with HTMX keeps logic on backend
- **Version Catalog:** Dependencies managed via `gradle/libs.versions.toml` for centralized version control
  - Run `./gradlew versionCatalogUpdate` to automatically update all dependencies
  - IDE provides type-safe autocomplete for `libs.xxx` references
  - Bundles group related dependencies (e.g., `libs.bundles.spring.boot`)

## Available MCP servers

- context7 for documentation fetching, especially for new libraries and versions
- puppeteer for browser automation, so you can verify developed code
- sequential-thinking for planning more complex tasks
- sqlite to access the DB of the application to check current schemas & data

Use them whenever it is necessary.

## Coding Guidelines

As this is POC, the main rule is to quickly get effect.
The testing should be limited to minimum - just unit tests to verify important logic.
Code should be concise and readable with minimum comments, just to understand non-trivial aspects.

### Code Conventions

- Prefer `val` for immutable local variables (Kotlin best practice)
- Import classes whenever possible; on conflicts prefer project classes
- Use `// language=<LANG>` comments before multi-line strings (SQL, JSON, XML) for IDE highlighting
- No javadoc/kdoc unless it brings value - avoid trivial documentation
- Comments only for non-obvious logic; code should be self-documenting

### Database Conventions (Exposed/SQLite)

- Use snake_case for table and column names
- Use INTEGER sequences for primary keys where appropriate
- Add indexes for frequently queried columns and foreign keys
- Use TEXT columns for strings (SQLite doesn't distinguish from VARCHAR)
- Meaningful names for constraints for easier debugging

### Testing Guidelines

- Test naming: `should <description space separated>` (with spaces)
- Label test sections with comments: `given`, `when`, `then`, `and`
- Don't mock data objects (DTOs, entities) - only services/operations
- Common test values as fields, not variables per test
- Run only affected tests, not full test suite (saves time)

### Best Practices

**Transaction Management:**
- Mark service methods with `@Transactional` when multiple DB operations needed
- Ensures atomicity and rollback on errors

**SQL Injection Prevention:**
- Never concatenate user input into queries
- Use Exposed DSL safely (it's parameterized by default)

**Resource Management:**
- Use `use {}` blocks for auto-closing resources (files, streams, connections)

**REST API:**

- REST API endpoints should be exposed under `/rest-api/` path
- Whenever possible, use the simplest controller approach - result returned as DTO and response code defined with `@ResponseStatus`
- For error responses create and keep updated `@RestControllerAdvice` bean with custom exception handlers

### Development recommendations

- Use IDE diagnostics to validate code after implementation
- Use context7 MCP for documentation of new libraries/versions
- Use sequential-thinking MCP for complex code tasks
- Use puppeteer MCP to verify developed UI functionality
- Use sqlite MCP to check database schema and data
- Use drawio MCP server for creating/updating draw.io diagrams. If MCP server is unavailable or not working, explicitly inform the user to install or fix it before proceeding. When creating diagrams: use actual HTML tags (`<b>`, `<br>`) not escaped HTML (`&lt;b&gt;`), and select appropriate shapes/icons for elements from the shape library.
- Use Web Search freely to find some sample resources, examples, solutions of problems, etc.
- Create controllers per page. All endpoints used by the page should have a common root path, so they are easy to identify. This is not applicable to api calls - which should be grouped by resource, typically to REST endpoints.

