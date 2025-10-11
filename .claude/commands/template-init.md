---
description: Initialize template with new package name (replaces com.example)
allowed-tools: Grep, Read, Edit, Write, Glob, Bash(mv:*), Bash(mkdir:*), Bash(rmdir:*), Bash(./gradlew build:*), Bash(./gradlew clean:*), Bash(tree:*)
---

# Template Initialization

Initialize this template project with a new package name.

## Instructions

1. Ask the user for the new base package name (e.g., `com.mycompany.myproject`)
2. Validate the package name format (lowercase, dots as separators)
3. Find all occurrences of `com.example` in the project
4. Replace all occurrences in file contents (*.kt, *.kts, *.yml, *.md, *.xml, *.properties)
5. Rename source directories from `com/example` to the new package path structure
6. Repeat grep search until no more `com.example` occurrences are found
7. Run `./gradlew build` to verify the changes work

Use Grep, Read, Edit, and Bash tools as needed. Do not ask for permission for each operation - execute the full replacement process autonomously.
