# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Rick and Morty Android App - A modern Android application that displays characters from the Rick and Morty API with infinite scroll pagination, built using Clean Architecture and Jetpack Compose.

**Key Technologies:**
- Kotlin 2.0.21
- Jetpack Compose (Material3)
- Android Gradle Plugin 8.13.2
- Minimum SDK: 29 (Android 10)
- Target SDK: 36
- Compile SDK: 36
- JVM Target: 11
- Koin 4.0.0 (Dependency Injection)
- Ktor 3.0.2 (HTTP Client)
- Coil 3.0.4 (Image Loading)
- ktlint 12.1.2 (Code Style)
- kotlinx.serialization (JSON parsing)

## Build Commands

### Build the project
```bash
./gradlew build
```

### Run tests
```bash
# Run unit tests
./gradlew test

# Run instrumented tests (requires emulator or connected device)
./gradlew connectedAndroidTest

# Run specific test class
./gradlew test --tests com.arkana.rikandmortyandroid_arkana.ExampleUnitTest
```

### Install and run the app
```bash
# Install debug build on connected device/emulator
./gradlew installDebug

# Build and install release (unsigned)
./gradlew installRelease
```

### Lint and code quality
```bash
# Run lint checks
./gradlew lint

# Run ktlint format (auto-fix code style)
./gradlew ktlintFormat

# Run ktlint check (report code style issues)
./gradlew ktlintCheck

# Clean build artifacts
./gradlew clean
```

## Project Structure

**Package name:** `com.arkana.rikandmortyandroid`

**Source code organization:**
```
app/src/main/java/com/arkana/rikandmortyandroid/
├── RickAndMortyApplication.kt       # Application class (Koin + Coil setup)
├── MainActivity.kt                   # Single activity entry point
│
├── di/                              # Dependency Injection (Koin)
│   ├── NetworkModule.kt             # Ktor HttpClient, ApiClient
│   ├── ImageModule.kt               # Coil ImageLoader configuration
│   ├── AppModule.kt                 # Repositories, ViewModels
│   └── KoinModules.kt               # Module aggregation
│
├── data/                            # Data Layer
│   ├── common/network/
│   │   ├── HttpClient.kt            # Ktor client singleton
│   │   └── ApiClient.kt             # Generic API wrapper
│   │
│   └── character/
│       ├── dto/                     # Data Transfer Objects
│       │   ├── CharacterResponseDto.kt
│       │   ├── CharacterListResponseDto.kt
│       │   ├── InfoDto.kt
│       │   └── StatusEnum.kt
│       │
│       └── repository/              # Repository Pattern
│           ├── CharacterRepository.kt      # Interface
│           └── CharacterRepositoryImpl.kt  # Implementation
│
└── ui/                              # UI Layer (Compose)
    ├── theme/                       # Material3 theming
    │   ├── Color.kt
    │   ├── Theme.kt
    │   └── Type.kt
    │
    ├── common/screens/components/   # Reusable components
    │   ├── AppContainer.kt          # Scaffold wrapper
    │   └── StateViews.kt            # ErrorView, EmptyView
    │
    └── character/
        ├── screens/
        │   ├── CharacterListScreen.kt         # Main screen
        │   └── components/
        │       └── CharacterItemScreen.kt     # Character card
        │
        ├── viewmodel/
        │   └── CharacterListViewModel.kt      # State management
        │
        └── state/
            └── CharacterListWrapper.kt        # UI State definition
```
- `app/src/test/` - Unit tests
- `app/src/androidTest/` - Instrumented Android tests

**Build configuration:**
- `build.gradle.kts` - Root project build configuration
- `app/build.gradle.kts` - App module build configuration with Compose setup
- `gradle/libs.versions.toml` - Version catalog for dependency management
- `settings.gradle.kts` - Project settings and module inclusion

## Architecture Notes

### Clean Architecture Layers

**1. Data Layer** (`data/`)
- **DTOs**: Kotlinx.serialization models matching API responses
- **Repository Pattern**: Interface + Implementation separation
- **CharactersPage**: Wrapper containing list + pagination metadata
- **Result<T>**: Kotlin's built-in Result for error handling

**2. UI Layer** (`ui/`)
- **Composables**: Declarative UI with Jetpack Compose
- **ViewModels**: State management with StateFlow
- **UI State**: Single source of truth pattern
- **Navigation**: Currently single screen (expandable)

**3. DI Layer** (`di/`)
- **Koin modules**: NetworkModule, ImageModule, AppModule
- **Singleton patterns**: HttpClient, ImageLoader
- **ViewModel injection**: Using `koinViewModel()`

### Key Patterns

**State Management**
```kotlin
data class State(
    val loading: Boolean = false,
    val loadingMore: Boolean = false,
    val characters: List<CharacterResponseDto> = emptyList(),
    val error: String? = null,
    val currentPage: Int = 1,
    val totalPages: Int = 1,
) {
    val hasMorePages: Boolean get() = currentPage < totalPages
}
```

**Infinite Scroll Pagination**
- Uses `derivedStateOf` to detect scroll position
- Loads next page when user is 5 items from bottom
- Accumulates results: `state.characters + result.characters`
- Separate loading states: `loading` (initial) vs `loadingMore` (pagination)

**Error Handling**
- Repository returns `Result<T>` (success/failure)
- ViewModel updates state with error message
- UI shows ErrorView with retry button when no data
- Maintains existing data if pagination fails

### Technical Details

**Dependency Injection (Koin)**
- Modules in `di/` directory
- Initialized in `RickAndMortyApplication.onCreate()`
- ViewModels: `koinViewModel<T>()` in Composables
- Other dependencies: Constructor injection or `get()`

**Networking (Ktor)**
- Client: Singleton in `KtorClient.instance`
- Wrapper: Generic `ApiClient` with typed methods
- Base URL: `https://rickandmortyapi.com/api`
- Features: JSON serialization, logging, 30s timeout
- Content negotiation with kotlinx.serialization

**Image Loading (Coil)**
- Custom ImageLoader with Ktor integration
- Memory + disk caching enabled
- Debug logging for error detection
- Configured in `ImageModule.kt`
- App implements `SingletonImageLoader.Factory`

**Code Style (ktlint)**
- Disabled rules for Android compatibility:
  - `package-name` (allows underscores)
  - `function-naming` (allows @Composable naming)
  - `no-wildcard-imports` (Android convention)
  - `filename` (flexibility for Android)
- Run `./gradlew ktlintFormat` before commits

## Features Implemented

### ✅ Character List
- Display all 826 characters from Rick and Morty API
- Material3 card design with:
  - Character image (loaded with Coil)
  - Character name
  - Status indicator (color-coded dot: green/red/gray)
  - Status text (Alive/Dead/Unknown)

### ✅ Infinite Scroll Pagination
- Automatic loading when scrolling near bottom
- 42 pages total (20 characters per page)
- Smooth pagination with loading indicator
- Buffer of 5 items before triggering next load

### ✅ State Management
- **Loading State**: Full-screen spinner on initial load
- **Error State**: ErrorView with retry button
- **Empty State**: EmptyView with search icon
- **Success State**: LazyColumn with character cards
- **Loading More**: Bottom loading indicator during pagination

### ✅ Error Handling
- Network error detection and display
- Retry mechanism on failures
- Graceful degradation (keeps existing data on pagination error)

## Dependencies

All dependencies are managed via the version catalog in `gradle/libs.versions.toml`.

**Adding new dependencies:**
1. Add version to `[versions]` section
2. Add library to `[libraries]` section
3. Reference in `app/build.gradle.kts` using `libs.` prefix

**Current dependencies:**

*Core Android*
- androidx-core-ktx: 1.15.0
- androidx-lifecycle-runtime-ktx: 2.8.7
- androidx-activity-compose: 1.9.3

*Compose*
- compose-bom: 2024.12.01
- compose-ui, compose-ui-graphics, compose-ui-tooling-preview
- compose-material3

*Networking*
- ktor-client-core: 3.0.2
- ktor-client-android: 3.0.2
- ktor-client-content-negotiation: 3.0.2
- ktor-serialization-kotlinx-json: 3.0.2
- ktor-client-logging: 3.0.2

*Dependency Injection*
- koin-android: 4.0.0
- koin-core: 4.0.0
- koin-compose: 4.0.0

*Image Loading*
- coil-compose: 3.0.4
- coil-network-ktor: 3.0.4

*Testing*
- junit: 4.13.2
- androidx-junit: 1.2.1
- androidx-espresso-core: 3.6.1
- androidx-compose-ui-test-junit4

## Future Enhancements

**Recommended next steps:**

1. **Navigation**: Add character detail screen
2. **Search/Filter**: Implement search by name and filter by status
3. **Local Caching**: Add Room database for offline support
4. **Domain Layer**: Create domain models and use cases
5. **Testing**: Unit tests for ViewModels and Repository
6. **Favorites**: Local persistence for favorite characters
7. **Dark Mode**: Implement theme switching
8. **Animations**: Add transitions and loading animations