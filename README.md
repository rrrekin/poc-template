# POC Template Project

A flexible Spring Boot + Kotlin template for rapid Proof of Concept development. This template provides a modern tech stack with minimal boilerplate, enabling quick prototyping and experimentation.

## Features

- 🚀 **Modern Stack** - Spring Boot 3.5.6, Kotlin 2.2.20, JVM 21
- 🗄️ **Database Ready** - Exposed (Kotlin SQL DSL) + SQLite embedded database
- 🎨 **Server-Side Rendering** - Thymeleaf with HTMX for dynamic updates
- 💫 **Reactive UI** - Alpine.js for client-side interactivity
- 📊 **Data Visualization** - ApexCharts integration
- 🤖 **AI Ready** - OpenAI API client + local embeddings (ONNX)
- 📁 **File Processing** - Excel (Apache POI) and CSV support
- 🎯 **No Auth** - Perfect for POCs and demos

## Prerequisites

- **JDK 21** or higher
- **Gradle** (included via wrapper)
- **Git** (optional)

## Quick Start

### 1. Clone or Use Template

```bash
# If using as template, navigate to your project directory
cd /path/to/your/project
```

### 2. Build the Project

```bash
./gradlew build
```

### 3. Run the Application

```bash
./gradlew bootRun
```

### 4. Open Browser

Navigate to: [http://localhost:8080/dashboard](http://localhost:8080/dashboard)

## Project Structure

```
src/
├── main/
│   ├── kotlin/com/example/
│   │   ├── poc/
│   │   │   └── TemplateApplication.kt     # Main application
│   │   ├── web/
│   │   │   └── DashboardController.kt     # Web page controllers
│   │   ├── rest/                          # REST API controllers
│   │   ├── domain/
│   │   │   └── UserService.kt             # Business logic
│   │   └── persistence/
│   │       ├── User.kt                    # Data models & tables
│   │       └── DatabaseConfig.kt          # DB configuration
│   └── resources/
│       ├── application.yml                # Configuration
│       ├── templates/                     # Thymeleaf templates
│       │   ├── layout.html               # Base layout
│       │   ├── dashboard.html            # Demo dashboard
│       │   └── fragments/                # Reusable fragments
│       └── static/
│           ├── css/                      # Custom CSS
│           └── js/                       # Custom JavaScript
└── test/
    └── kotlin/                           # Unit tests
```

## Technology Stack

### Backend
- **Spring Boot 3.5.6** - Application framework
- **Kotlin 2.2.20** - Programming language
- **JVM 21** - Runtime environment

### Database
- **Exposed 0.61.0** - Kotlin SQL DSL framework
- **SQLite** - Embedded SQL database

### Frontend
- **Thymeleaf** - Server-side templating
- **HTMX 2.0.7** - Dynamic HTML updates without JavaScript
- **Alpine.js 3.15.0** - Minimal reactive framework
- **Bulma 1.0.4** - CSS framework (compact design)
- **ApexCharts** - Data visualization library

### AI & ML
- **OpenAI Java SDK 4.3.0** - OpenAI API integration
- **ONNX Runtime 1.23.1** - Local embeddings generation

### File Processing
- **Apache POI 5.4.1** - Excel file handling
- **Apache Commons CSV 1.14.1** - CSV parsing

## Configuration

### Application Settings

Edit `src/main/resources/application.yml`:

```yaml
server:
  port: 8080  # Change port if needed

logging:
  level:
    com.example: DEBUG  # Adjust log level
```

### OpenAI API Configuration

Set environment variable:

```bash
export OPENAI_API_KEY=your-api-key-here
```

Or add to your IDE run configuration.

### Development Mode

DevTools is enabled by default:
- Auto-restart on code changes
- LiveReload for templates
- Template caching disabled

## Demo Dashboard

The included dashboard demonstrates:

### 1. HTMX Integration
- Click **"Load Widget"** to see partial page updates
- Server returns HTML fragments, no JSON needed
- No page reload required

### 2. Alpine.js Reactivity
- Click **"Show/Hide"** for client-side state management
- Counter button demonstrates reactive updates
- Pure declarative syntax in HTML

### 3. ApexCharts Visualization
- Auto-loading chart on page load
- Click **"Load Chart"** to refresh data
- Responsive and interactive charts

### 4. Database Demo (Exposed + SQLite)
- Live CRUD operations with HTMX
- Click **"Add New User"** to create database entries
- Delete users with trash icon
- Table auto-updates without page reload
- Sample data pre-seeded (Hitchhiker's Guide references!)

## Adding New Features

### 1. Create a Controller

```kotlin
@Controller
@RequestMapping("/my-feature")
class MyFeatureController {

    @GetMapping
    fun index(model: Model): String {
        model.addAttribute("data", "Hello POC!")
        return "my-feature"
    }
}
```

### 2. Create Template

Create `src/main/resources/templates/my-feature.html`:

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      th:replace="~{layout :: html(content=~{::content})}">
<body>
    <div th:fragment="content">
        <h1 class="title">My Feature</h1>
        <p th:text="${data}">Content here</p>
    </div>
</body>
</html>
```

### 3. Add Navigation

Edit `layout.html` navbar:

```html
<a class="navbar-item" href="/my-feature">My Feature</a>
```

## Common Patterns

### HTMX Partial Update

**Controller:**
```kotlin
@GetMapping("/data")
fun loadData(model: Model): String {
    model.addAttribute("items", listOf("A", "B", "C"))
    return "fragments/data :: content"
}
```

**Template:**
```html
<button hx-get="/data" hx-target="#result">Load Data</button>
<div id="result"></div>
```

### Alpine.js Component

```html
<div x-data="{ count: 0, items: [] }">
    <button @click="count++">Clicked <span x-text="count"></span> times</button>
    <input x-model="search" placeholder="Search...">
</div>
```

### ApexCharts Integration

**Controller:**
```kotlin
@GetMapping("/api/chart-data")
@ResponseBody
fun chartData(): Map<String, Any> {
    return mapOf(
        "series" to listOf(mapOf("name" to "Data", "data" to listOf(1, 2, 3)))
    )
}
```

**JavaScript:**
```javascript
fetch('/api/chart-data')
    .then(r => r.json())
    .then(data => {
        new ApexCharts(document.querySelector("#chart"), {
            series: data.series,
            chart: { type: 'line' }
        }).render();
    });
```

## Using OpenAI API

Add service class:

```kotlin
@Service
class OpenAIService {
    private val client = OpenAI.builder()
        .apiKey(System.getenv("OPENAI_API_KEY"))
        .build()

    fun generateText(prompt: String): String {
        val response = client.chat().completions().create(
            ChatCompletionCreateParams.builder()
                .model("gpt-4")
                .messages(listOf(
                    ChatCompletionMessageParam.ofUser(prompt)
                ))
                .build()
        )
        return response.choices()[0].message().content()
    }
}
```

## Database Usage (Exposed + SQLite)

### Define Table and Model

Located in `com.example.persistence` package:

```kotlin
// Table definition using Exposed DSL
object Users : Table("users") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val email = varchar("email", 255)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    override val primaryKey = PrimaryKey(id)
}

// Data class for application use
data class User(
    val id: Int? = null,
    val name: String,
    val email: String,
    val createdAt: LocalDateTime? = null
)
```

### Create Service with CRUD Operations

Located in `com.example.domain` package:

```kotlin
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

    fun createUser(name: String, email: String): User = transaction {
        val id = Users.insert {
            it[Users.name] = name
            it[Users.email] = email
        } get Users.id
        User(id = id, name = name, email = email)
    }

    fun deleteUser(id: Int): Boolean = transaction {
        Users.deleteWhere { Users.id eq id } > 0
    }
}
```

### Use in Controller

Located in `com.example.web` package:

```kotlin
@Controller
class UserController(private val userService: UserService) {
    @GetMapping("/users")
    fun listUsers(model: Model): String {
        model.addAttribute("users", userService.getAllUsers())
        return "users"
    }

    @PostMapping("/users")
    fun createUser(@RequestParam name: String, @RequestParam email: String): String {
        userService.createUser(name, email)
        return "redirect:/users"
    }
}
```

### Database Configuration

Database is auto-configured via `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:sqlite:data/template.db
    driver-class-name: org.sqlite.JDBC
  exposed:
    generate-ddl: true  # Auto-create tables
    show-sql: true      # Log SQL (development)
```

Database file: `data/template.db` (auto-created on first run)

### Switching Databases

To switch to PostgreSQL or MySQL:

1. Update `application.yml` datasource URL
2. Add appropriate JDBC driver dependency
3. No code changes needed! (Exposed handles dialect automatically)

## Excel & CSV Processing

### Reading Excel

```kotlin
val workbook = WorkbookFactory.create(File("data.xlsx"))
val sheet = workbook.getSheetAt(0)
sheet.forEach { row ->
    row.forEach { cell ->
        println(cell.toString())
    }
}
```

### Reading CSV

```kotlin
val reader = CSVFormat.DEFAULT.withFirstRecordAsHeader()
    .parse(FileReader("data.csv"))
reader.forEach { record ->
    val value = record.get("ColumnName")
    println(value)
}
```

## Testing

### Run Tests

```bash
./gradlew test
```

### Run with Coverage

```bash
./gradlew test jacocoTestReport
```

## Building for Production

### Create JAR

```bash
./gradlew bootJar
```

Output: `build/libs/template-0.0.1-SNAPSHOT.jar`

### Run JAR

```bash
java -jar build/libs/template-0.0.1-SNAPSHOT.jar
```

### Docker (Optional)

Create `Dockerfile`:

```dockerfile
FROM eclipse-temurin:21-jre
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

Build and run:

```bash
docker build -t poc-template .
docker run -p 8080:8080 poc-template
```

## Useful Gradle Commands

```bash
# Clean build
./gradlew clean build

# Run without daemon (faster for single use)
./gradlew bootRun --no-daemon

# Continuous build (auto-rebuild on changes)
./gradlew build --continuous

# Check for dependency updates (via Version Catalog - Recommended)
./gradlew versionCatalogUpdate  # Auto-updates gradle/libs.versions.toml

# Alternative: Check for dependency updates (via Gradle Versions Plugin)
./gradlew dependencyUpdates
./gradlew dependencyUpdates --refresh-dependencies  # Force refresh

# Generate dependency tree
./gradlew dependencies
```

### Dependency Management with Version Catalog

This project uses [Gradle Version Catalogs](https://docs.gradle.org/current/userguide/version_catalogs.html) for centralized dependency management. All dependencies are defined in `gradle/libs.versions.toml`.

**Benefits:**
- **Type-safe accessors**: IDE autocomplete for `libs.xxx` references
- **Centralized versions**: All versions in one file
- **Dependency bundles**: Logical groupings (e.g., `libs.bundles.spring.boot`)
- **Automatic updates**: Run `./gradlew versionCatalogUpdate` to update all dependencies

**Workflow:**
1. Run `./gradlew versionCatalogUpdate` to check and update versions
2. Review changes in `gradle/libs.versions.toml`
3. Test with `./gradlew build` and `./gradlew test`
4. Commit the updated catalog


## IDE Setup

### IntelliJ IDEA (Recommended)

1. **Open Project**: File → Open → Select `build.gradle.kts`
2. **Kotlin Plugin**: Should be auto-detected
3. **Run Configuration**: Edit Configurations → Add Spring Boot → Select `TemplateApplication`
4. **Enable DevTools**: Preferences → Build → Compiler → Build project automatically

### VS Code

1. Install extensions:
   - Kotlin Language
   - Spring Boot Extension Pack
   - Gradle for Java
2. Open folder
3. Run with Gradle task

## Troubleshooting

### Port Already in Use

Change port in `application.yml`:
```yaml
server:
  port: 8081
```

### Kotlin Version Issues

Ensure `build.gradle.kts` has:
```kotlin
kotlin("jvm") version "2.2.20"
kotlin("plugin.spring") version "2.2.20"
```

### WebJars Not Loading

Check WebJars locator is included:
```kotlin
implementation("org.webjars:webjars-locator-core:0.59")
```

### Template Changes Not Reflecting

Disable cache in `application.yml`:
```yaml
spring:
  thymeleaf:
    cache: false
```

## Documentation Links

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [HTMX Documentation](https://htmx.org/docs/)
- [Alpine.js Documentation](https://alpinejs.dev/)
- [Bulma Documentation](https://bulma.io/documentation/)
- [ApexCharts Documentation](https://apexcharts.com/docs/)
- [OpenAI API Documentation](https://platform.openai.com/docs/)
- [Exposed Documentation](https://www.jetbrains.com/help/exposed/get-started-with-exposed.html)

## Architecture Documentation

See [TECH_STACK.md](TECH_STACK.md) for detailed technology decisions and architecture notes.

## Contributing

This is a template project. Fork and customize for your POC needs!

## License

This template is provided as-is for POC development purposes.

---

**Happy Prototyping! 🚀**

For questions or issues, refer to the documentation links above or Spring Boot/Kotlin community resources.

---

## Building a zip file with the template

```
zip -r ../template.zip . -x "*.git/*" ".git/*" ".idea/*" "*.iml" ".gradle/*" "build/*" "*/build/*"
```

## Cloning the Template

### Claude MCP configuration

```bash
claude mcp add-json "sequential-thinking" '{"command":"npx","args":["-y","@modelcontextprotocol/server-sequential-thinking"]}'
claude mcp add-json context7 '{"command":"npx","args":["-y","@upstash/context7-mcp@latest"]}'
claude mcp add-json puppeteer '{"command":"npx","args":["-y","puppeteer-mcp-server"]}'
claude mcp add-json sqlite '{"command": "npx","args": ["-y","mcp-sqlite","data/database.db"]}'
```

Optional:

```bash
claude mcp add-json excalidraw '{"command": "node","args": ["/Users/michalrudewicz/ai/mcp_excalidraw/dist/index.js"]}'
claude mcp add-json drawio '{"command":"npx","args":["-y","drawio-mcp-server"]}'
```

### Rename the project after cloning

Claude code prompt:

```text
review the project and replace all references to `POC Template` with `<new-name>`
```
