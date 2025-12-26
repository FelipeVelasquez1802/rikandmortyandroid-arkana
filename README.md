# Rick and Morty Android App

<div align="center">

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android)
![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-7F52FF?logo=kotlin)
![Compose](https://img.shields.io/badge/Jetpack_Compose-Material3-4285F4?logo=jetpackcompose)
![Min SDK](https://img.shields.io/badge/Min_SDK-29-orange)
![License](https://img.shields.io/badge/License-MIT-green)

Una aplicación moderna de Android que muestra personajes de Rick and Morty con scroll infinito y arquitectura limpia.

[Features](#features) • [Screenshots](#screenshots) • [Tech Stack](#tech-stack) • [Architecture](#architecture) • [Getting Started](#getting-started)

</div>

---

## 📱 Features

- ✅ **Lista de Personajes**: Visualiza los 826+ personajes de la serie
- ✅ **Scroll Infinito**: Carga automática de páginas al hacer scroll
- ✅ **Estados de UI**: Loading, Error, Empty, y Success states
- ✅ **Manejo de Errores**: Retry automático con feedback visual
- ✅ **Material Design 3**: UI moderna y consistente
- ✅ **Carga de Imágenes**: Cache eficiente con Coil
- ✅ **Offline Resilience**: Mantiene datos en caso de error de paginación

---

## 🖼️ Screenshots

| Loading State | Character List | Error State |
|:-------------:|:--------------:|:-----------:|
| ![Loading](docs/loading.png) | ![List](docs/list.png) | ![Error](docs/error.png) |

*Screenshots coming soon*

---

## 🛠️ Tech Stack

### Core
- **Language**: Kotlin 2.0.21
- **UI Framework**: Jetpack Compose
- **Design System**: Material3
- **Min SDK**: 29 (Android 10)
- **Target SDK**: 36

### Libraries

| Category | Library | Version | Purpose |
|----------|---------|---------|---------|
| **Networking** | Ktor | 3.0.2 | HTTP client |
| **DI** | Koin | 4.0.0 | Dependency injection |
| **Images** | Coil | 3.0.4 | Image loading & caching |
| **Serialization** | kotlinx.serialization | - | JSON parsing |
| **Code Style** | ktlint | 12.1.2 | Code formatting |
| **Testing** | JUnit | 4.13.2 | Unit testing |

---

## 🏗️ Architecture

Este proyecto sigue **Clean Architecture** con separación de capas:

```
┌─────────────────────────────────────────────────────────┐
│                      UI Layer                           │
│  ┌─────────────┐  ┌──────────────┐  ┌───────────────┐ │
│  │  Composables│  │  ViewModels  │  │   UI States   │ │
│  └─────────────┘  └──────────────┘  └───────────────┘ │
└─────────────────────────────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────┐
│                     Data Layer                          │
│  ┌─────────────┐  ┌──────────────┐  ┌───────────────┐ │
│  │ Repositories│  │     DTOs     │  │   ApiClient   │ │
│  └─────────────┘  └──────────────┘  └───────────────┘ │
└─────────────────────────────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────┐
│                      DI Layer                           │
│  ┌─────────────┐  ┌──────────────┐  ┌───────────────┐ │
│  │   Network   │  │    Image     │  │      App      │ │
│  │   Module    │  │    Module    │  │    Module     │ │
│  └─────────────┘  └──────────────┘  └───────────────┘ │
└─────────────────────────────────────────────────────────┘
```

### Estructura de Carpetas

```
app/src/main/java/com/arkana/rikandmortyandroid/
│
├── 📱 RickAndMortyApplication.kt     # Application class
├── 📱 MainActivity.kt                 # Single activity
│
├── 💉 di/                             # Dependency Injection
│   ├── NetworkModule.kt
│   ├── ImageModule.kt
│   └── AppModule.kt
│
├── 📊 data/                           # Data Layer
│   ├── common/network/
│   │   ├── HttpClient.kt
│   │   └── ApiClient.kt
│   │
│   └── character/
│       ├── dto/                       # Data Transfer Objects
│       └── repository/                # Repository Pattern
│
└── 🎨 ui/                             # UI Layer
    ├── theme/                         # Material3 Theme
    ├── common/screens/components/     # Reusable Components
    └── character/
        ├── screens/                   # Screens & Components
        ├── viewmodel/                 # State Management
        └── state/                     # UI State Models
```

### Patrones Clave

#### 🔄 State Management
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

#### 📜 Infinite Scroll
- Detección automática de scroll con `derivedStateOf`
- Carga anticipada (5 items antes del final)
- Acumulación de resultados
- Estados separados: `loading` vs `loadingMore`

#### ⚠️ Error Handling
- `Result<T>` para operaciones asíncronas
- UI adaptativa según estado de error
- Botón de retry en ErrorView
- Persistencia de datos en errores de paginación

---

## 🚀 Getting Started

### Prerequisitos

- Android Studio Hedgehog (2023.1.1) o superior
- JDK 11 o superior
- Android SDK 29+
- Emulador o dispositivo físico

### Instalación

1. **Clonar el repositorio**
```bash
git clone https://github.com/tu-usuario/rikandmortyandroidarkana.git
cd rikandmortyandroidarkana
```

2. **Abrir en Android Studio**
```bash
# Opción 1: Desde terminal
studio .

# Opción 2: File > Open en Android Studio
```

3. **Sincronizar Gradle**
```bash
./gradlew build
```

4. **Ejecutar la app**
- Presiona `Run` (▶️) en Android Studio
- O desde terminal:
```bash
./gradlew installDebug
```

### Comandos Útiles

```bash
# Compilar
./gradlew assembleDebug

# Ejecutar tests
./gradlew test

# Formatear código
./gradlew ktlintFormat

# Verificar estilo de código
./gradlew ktlintCheck

# Limpiar build
./gradlew clean
```

---

## 🧪 Testing

```bash
# Unit tests
./gradlew test

# Instrumented tests (requiere emulador)
./gradlew connectedAndroidTest

# Test con coverage
./gradlew testDebugUnitTest jacocoTestReport
```

### Estado Actual
- ⚠️ Tests unitarios: Pendiente
- ⚠️ Tests de integración: Pendiente
- ⚠️ Tests de UI: Pendiente

---

## 📡 API

Este proyecto consume la [Rick and Morty API](https://rickandmortyapi.com/)

**Endpoints utilizados:**
- `GET /character?page={page}` - Lista de personajes paginada
- `GET /character/{id}` - Detalle de personaje (preparado)

**Características del API:**
- ✅ REST API pública
- ✅ Sin autenticación
- ✅ 826+ personajes
- ✅ 42 páginas (20 por página)
- ✅ Información de paginación incluida

---

## 🎨 Design Decisions

### Por qué Ktor en lugar de Retrofit?
- ✅ Soporte multiplataforma (preparado para KMM)
- ✅ Coroutines nativas
- ✅ Configuración más simple
- ✅ Menor overhead

### Por qué Koin en lugar de Hilt?
- ✅ Sintaxis más simple
- ✅ No requiere anotaciones
- ✅ Setup más rápido
- ✅ Mejor para proyectos pequeños/medianos

### Por qué Coil en lugar de Glide?
- ✅ Diseñado para Compose
- ✅ Soporte nativo de Coroutines
- ✅ Integración con Ktor
- ✅ Menor tamaño de librería

### Paginación Manual vs Paging 3?
- ✅ Control total del flujo
- ✅ Sin dependencias adicionales
- ✅ API simple (solo page param)
- ⚠️ Migrar a Paging 3 si se necesita caché complejo

---

## 📝 Code Style

Este proyecto usa **ktlint** con reglas personalizadas para Android:

```properties
# .editorconfig
[*.kt]
disabled_rules = package-name,function-naming,no-wildcard-imports,filename
```

**Convenciones:**
- ✅ Kotlin official code style
- ✅ Max line length: 120
- ✅ Imports: wildcards permitidos (Android convention)
- ✅ Composables: PascalCase

**Antes de commit:**
```bash
./gradlew ktlintFormat
```

---

## 🗺️ Roadmap

### v1.0 (Actual)
- [x] Lista de personajes
- [x] Infinite scroll pagination
- [x] Manejo de estados (loading, error, empty)
- [x] Material3 UI

### v1.1 (Próximo)
- [ ] Pantalla de detalle de personaje
- [ ] Búsqueda por nombre
- [ ] Filtros por status (Alive/Dead/Unknown)
- [ ] Modo oscuro

### v2.0 (Futuro)
- [ ] Caché local con Room
- [ ] Favoritos
- [ ] Compartir personajes
- [ ] Animaciones y transiciones
- [ ] Tests comprehensivos

---

## 🤝 Contributing

Las contribuciones son bienvenidas! Por favor:

1. Fork el proyecto
2. Crea tu feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push al branch (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

**Guidelines:**
- Ejecuta `./gradlew ktlintFormat` antes de commit
- Asegúrate que los tests pasen
- Sigue las convenciones de código existentes
- Actualiza la documentación si es necesario

---

## 📄 License

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.

---

## 👨‍💻 Author

**Felipe Velásquez** - [@arkana](https://github.com/tu-usuario)

---

## 🙏 Acknowledgments

- [Rick and Morty API](https://rickandmortyapi.com/) por el API público
- [Jetpack Compose](https://developer.android.com/jetpack/compose) por el framework UI
- [Rick and Morty](https://www.adultswim.com/videos/rick-and-morty) por la inspiración

---

## 📞 Contact

¿Preguntas o sugerencias? Abre un [issue](https://github.com/tu-usuario/rikandmortyandroidarkana/issues)

---

<div align="center">

**Hecho con ❤️ y Kotlin**

[⬆ Volver arriba](#rick-and-morty-android-app)

</div>