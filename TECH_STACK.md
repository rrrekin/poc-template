# Technology Stack

## Overview

This is a flexible template project designed for rapid POC (Proof of Concept) development. The stack prioritizes:
- Quick setup and development speed
- Modern, stable technologies
- Minimal boilerplate
- Server-side rendering with dynamic HTML updates
- AI integration capabilities
- Data visualization support

## Core Platform

### Spring Boot 3.4.10
- Latest stable version in the 3.x line
- Mature ecosystem with extensive autoconfiguration
- Excellent Kotlin support
- Production-ready features out of the box

### Kotlin 2.2.20
- Latest stable version
- Full Java interoperability
- Concise syntax reducing boilerplate
- Coroutines support for async operations
- Null safety

### JVM 21
- Current LTS (Long Term Support) version
- Modern features (virtual threads, pattern matching)
- Best balance of stability and performance
- Wide ecosystem support

## Web & Presentation Layer

### Thymeleaf
**Purpose:** Server-side HTML templating

**Why chosen:**
- Natural templating (valid HTML)
- Excellent Spring Boot integration
- Server-side rendering keeps logic on backend
- Works seamlessly with HTMX for partial updates

## Dynamic HTML Updates

### HTMX 1.x (CDN or WebJar)
**Purpose:** Partial page updates, AJAX without writing JavaScript

**Why chosen:**
- HTML-centric approach (13kB)
- Server returns HTML fragments, not JSON
- Perfect for complex popups and on-demand components
- Reduces JavaScript complexity
- Examples:
  ```html
  <button hx-get="/api/details" hx-target="#popup">Load Details</button>
  ```

### Alpine.js 3.x (CDN or WebJar)
**Purpose:** Client-side interactivity

**Why chosen:**
- Lightweight (15kB)
- Handles UI state (dropdowns, tabs, accordions)
- Perfect complement to HTMX
- No build step required
- Examples:
  ```html
  <div x-data="{ open: false }">
    <button @click="open = !open">Toggle</button>
  </div>
  ```

**HTMX + Alpine.js Pattern:**
- HTMX: Server interactions (load data, submit forms)
- Alpine.js: Client-side UI logic (show/hide, animations)

## UI Framework

### Bulma 0.9.x
**Purpose:** CSS framework for consistent, compact UI

**Why chosen:**
- Clean, compact design (ideal for data-heavy displays)
- No JavaScript dependencies (plays well with Alpine.js/HTMX)
- Flexbox-based, fully responsive
- Rapid prototyping with pre-built components
- Simple class-based approach
- Alternative: Bootstrap 5 also viable if team prefers

**Key features for POCs:**
- Tables optimized for data display
- Modal system for popups
- Form components
- Grid system

## Data Visualization

### ApexCharts
**Purpose:** Interactive data visualization and charts

**Why chosen:**
- Extensive chart types (line, bar, pie, scatter, heatmap, etc.)
- Excellent for dashboards
- Responsive and interactive
- Good documentation and examples
- Reasonable bundle size
- Dynamic data updates support

**Integration:** Include via CDN or npm, initialize in Alpine.js components or vanilla JS

**Example chart types:**
- Time series data
- Real-time updating charts
- Multi-series comparisons
- Heatmaps for data density

## Database

### Spring Data JDBC
**Purpose:** Repository-based database operations with Spring-managed transactions

**Why chosen:**
- Part of Spring Data - familiar repository pattern
- No ORM complexity (unlike JPA) - direct SQL mapping
- Auto-implements CRUD operations via `CrudRepository`
- Derived query methods from method names
- Spring-managed transactions with `@Transactional`
- Perfect for POC rapid development
- Easy to switch to other databases (PostgreSQL, MySQL, etc.)

**Key features:**
- `CrudRepository` interface for standard CRUD operations
- Entity classes with `@Table` and `@Id` annotations
- Derived query methods (e.g., `findByNameContaining`)
- `@Query` annotation for custom SQL
- Automatic resource management
- Spring transaction integration
- SQL initialization via `schema.sql` and `data.sql`

**Usage example:**
```kotlin
// Define entity class
@Table("users")
data class User(
    @Id val id: Int? = null,
    val name: String,
    val email: String,
    val createdAt: LocalDateTime? = null
)

// Repository interface - Spring auto-implements
interface UserRepository : CrudRepository<User, Int> {
    fun findByNameContaining(name: String): List<User>
}

// Service with transactions
@Service
class UserService(private val userRepository: UserRepository) {
    fun getAllUsers(): List<User> = userRepository.findAll().toList()

    fun getUserById(id: Int): User? = userRepository.findByIdOrNull(id)

    @Transactional
    fun createUser(name: String, email: String) = userRepository.save(User(name = name, email = email))

    @Transactional
    fun deleteUser(id: Int): Boolean {
        if (!userRepository.existsById(id)) return false
        userRepository.deleteById(id)
        return true
    }
}
```

### SQLite
**Purpose:** Embedded SQL database

**Why chosen:**
- Zero configuration - file-based database
- No separate server process required
- Perfect for POCs and demos
- Lightweight and fast
- Easy to share (single file)
- Can migrate to PostgreSQL/MySQL later

**Configuration:**
- Database file: `data/database.db`
- Auto-created on first run
- Schema initialized via `schema.sql`
- Seed data loaded via `data.sql`
- Portable across environments

**When to switch:**
- Production deployments → PostgreSQL/MySQL
- Multi-user concurrent access → PostgreSQL
- Complex transactions → PostgreSQL
- Keeping SQLite for POCs is perfectly fine!

## AI Integration

### OpenAI API Client

**Library:** `com.openai:openai-java`
**Version:** 4.3.0+

**Why chosen:**
- Official OpenAI Java SDK
- Actively maintained
- Support for latest models (GPT-4, GPT-4 Turbo, etc.)
- Maven Central availability

**Features:**
- Chat completions
- Embeddings
- Function calling
- Streaming support

### Local Embeddings

**Library:** ONNX Runtime + Sentence Transformers models
**Maven:** `com.microsoft.onnxruntime:onnxruntime`

**Why chosen:**
- Run embeddings locally without API calls
- Offline capability
- Cost-effective for high-volume embedding generation
- Compatible with HuggingFace models (all-MiniLM-L6-v2, etc.)

**Alternative:** Spring AI ONNX Transformers (if using Spring AI ecosystem)

## File Processing

### Apache POI 5.x
**Purpose:** Excel file reading/writing (XLS, XLSX)

**Dependencies:**
- `org.apache.poi:poi` - Core library
- `org.apache.poi:poi-ooxml` - Office Open XML support

**Why chosen:**
- Industry standard for Excel in Java/JVM
- Supports both old (.xls) and new (.xlsx) formats
- Mature, stable API
- Good Kotlin support

### Apache Commons CSV
**Purpose:** CSV file parsing and writing

**Why chosen:**
- Simple, focused library
- RFC 4180 compliant
- Handles edge cases (quotes, escaping)
- Low overhead

## Maven Dependencies Summary

```xml
<properties>
    <kotlin.version>2.2.20</kotlin.version>
    <spring-boot.version>3.4.10</spring-boot.version>
    <java.version>21</java.version>
</properties>

<dependencies>
    <!-- Spring Boot Starters -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>

    <!-- Kotlin -->
    <dependency>
        <groupId>org.jetbrains.kotlin</groupId>
        <artifactId>kotlin-stdlib</artifactId>
    </dependency>
    <dependency>
        <groupId>org.jetbrains.kotlin</groupId>
        <artifactId>kotlin-reflect</artifactId>
    </dependency>

    <!-- OpenAI -->
    <dependency>
        <groupId>com.openai</groupId>
        <artifactId>openai-java</artifactId>
        <version>4.3.0</version>
    </dependency>

    <!-- ONNX Runtime (Local Embeddings) -->
    <dependency>
        <groupId>com.microsoft.onnxruntime</groupId>
        <artifactId>onnxruntime</artifactId>
        <version>1.19.2</version>
    </dependency>

    <!-- Apache POI (Excel) -->
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi</artifactId>
        <version>5.3.0</version>
    </dependency>
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi-ooxml</artifactId>
        <version>5.3.0</version>
    </dependency>

    <!-- Apache Commons CSV -->
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-csv</artifactId>
        <version>1.12.0</version>
    </dependency>

    <!-- Spring Data JDBC + SQLite -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jdbc</artifactId>
    </dependency>
    <dependency>
        <groupId>org.xerial</groupId>
        <artifactId>sqlite-jdbc</artifactId>
        <version>3.51.1.0</version>
    </dependency>

    <!-- WebJars (Optional - can use CDN instead) -->
    <dependency>
        <groupId>org.webjars</groupId>
        <artifactId>htmx</artifactId>
        <version>1.9.12</version>
    </dependency>
    <dependency>
        <groupId>org.webjars.npm</groupId>
        <artifactId>alpinejs</artifactId>
        <version>3.14.1</version>
    </dependency>
    <dependency>
        <groupId>org.webjars</groupId>
        <artifactId>bulma</artifactId>
        <version>0.9.4</version>
    </dependency>
</dependencies>
```

## Architecture Pattern

### Request Flow for Server-Side Rendered Pages

1. **Initial Page Load**
   - Spring Controller → Thymeleaf template
   - Returns complete HTML with Bulma styling
   - Includes HTMX and Alpine.js via CDN/WebJar

2. **Dynamic Updates**
   - User interaction triggers HTMX request
   - Controller returns HTML fragment
   - HTMX swaps content into page
   - Alpine.js handles client-side UI state

3. **Data Visualization**
   - Controller provides data as JSON endpoint
   - ApexCharts initialized in page
   - Charts update dynamically

### Example Controller Pattern (Kotlin)

```kotlin
package com.example.web

@Controller
@RequestMapping("/dashboard")
class DashboardController {

    @GetMapping
    fun dashboard(model: Model): String {
        // Full page render
        return "dashboard"
    }

    @GetMapping("/widget")
    fun widget(model: Model): String {
        // HTMX partial update
        return "fragments/widget :: content"
    }

    @GetMapping("/chart-data")
    @ResponseBody
    fun chartData(): ChartData {
        // JSON for ApexCharts
        return ChartData(...)
    }
}
```

**Package Structure:**
- `com.example.poc` - Main application
- `com.example.web` - Web page controllers (Thymeleaf)
- `com.example.rest` - REST API controllers (JSON)
- `com.example.domain` - Business logic services
- `com.example.persistence` - Database models & configuration

## Development Workflow

### Quick POC Development Pattern

1. **Define schema** (`schema.sql`) and entity class with `@Table`, `@Id`
2. **Create repository interface** extending `CrudRepository`
3. **Create service layer** with `@Transactional` operations
4. **Create controller** with endpoints
5. **Build Thymeleaf template** with Bulma components
6. **Add HTMX** for dynamic interactions
7. **Sprinkle Alpine.js** for client-side UI logic
8. **Integrate ApexCharts** for visualizations
9. **Connect AI/file processing** as needed

### No Build Step Required

- HTMX, Alpine.js, Bulma, ApexCharts can all be CDN-loaded
- No webpack, npm scripts, or frontend build process
- Entire POC can be developed in backend codebase
- Rapid iteration with Spring Boot DevTools

## Security Notes

**For POC/Template - NO AUTHENTICATION:**
- No Spring Security dependency
- Endpoints are open
- Suitable for internal demos, not production

**When moving to production:**
- Add `spring-boot-starter-security`
- Implement authentication (OAuth2, JWT, etc.)
- Add CSRF protection
- Secure API endpoints

## Future Extension Points

### Easy to Add:
- **Different database:** Switch SQLite to PostgreSQL, MySQL, or H2
- **Database migrations:** Add Flyway or Liquibase
- **WebSockets:** For real-time updates (HTMX supports SSE)
- **Background jobs:** Spring @Scheduled, Quartz
- **Testing:** Spring Boot Test, MockK (Kotlin)
- **API documentation:** SpringDoc OpenAPI

### Alternative Approaches:
- **Frontend framework:** If POC grows, can migrate to React/Vue
- **GraphQL:** Instead of REST endpoints
- **Microservices:** If POC validates concept needing scale

## Version Update Strategy

- **Spring Boot:** Follow 3.x releases (quarterly patches)
- **Kotlin:** Update to latest 2.x stable
- **Dependencies:** Check for updates quarterly
- **Security patches:** Apply immediately

## References

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [HTMX Documentation](https://htmx.org/docs/)
- [Alpine.js Documentation](https://alpinejs.dev/)
- [Bulma Documentation](https://bulma.io/documentation/)
- [ApexCharts Documentation](https://apexcharts.com/docs/)
- [OpenAI API Documentation](https://platform.openai.com/docs/)
- [Spring Data JDBC Documentation](https://docs.spring.io/spring-data/jdbc/reference/)

---

**Last Updated:** 2025-10-11
**Template Version:** 1.0.0
