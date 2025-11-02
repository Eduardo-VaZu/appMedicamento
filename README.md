# 💊 App Medicamento

Aplicación Android nativa para la gestión completa de medicamentos con operaciones CRUD (Crear, Leer, Actualizar, Eliminar) conectada a una API REST.

## 📋 Descripción

App Medicamento es una aplicación móvil desarrollada en Kotlin que permite administrar un catálogo de medicamentos de forma intuitiva. La aplicación se conecta a una API REST para persistir los datos y ofrece una interfaz moderna con Material Design.

## ✨ Características

- 📱 **Listado de medicamentos**: Visualización de todos los medicamentos con RecyclerView
- ➕ **Registro**: Agregar nuevos medicamentos con validación de datos
- ✏️ **Actualización**: Editar información de medicamentos existentes
- 🗑️ **Eliminación**: Borrar medicamentos con confirmación
- 🖼️ **Imágenes**: Carga de imágenes desde URL con Glide
- 🔄 **Sincronización**: Actualización automática desde el servidor
- ⚡ **Indicadores de carga**: ProgressBar durante operaciones asíncronas
- ✅ **Validación**: Verificación de campos y formatos

## 🛠️ Tecnologías

- **Lenguaje**: Kotlin
- **SDK mínimo**: Android 7.0 (API 24)
- **SDK objetivo**: Android 14+ (API 36)
- **Arquitectura**: MVC (Model-View-Controller)
- **Networking**: 
  - Retrofit 2.5.0
  - Gson Converter 2.5.0
- **Carga de imágenes**: Glide 4.11.0
- **UI**: Material Design Components

## 📦 Estructura del Proyecto

```
app/src/main/java/pe/app/appmedicamento/
├── adapter/
│   └── MedicamentosAdapter.kt       # Adaptador del RecyclerView
├── model/
│   └── Medicamento.kt               # Modelo de datos
├── service/
│   └── ApiMedicamento.kt            # Interface de Retrofit
├── utils/
│   ├── ApiUtils.kt                  # Configuración de API
│   └── RetrofitClient.kt            # Cliente HTTP
├── view/
│   └── MedicamentoView.kt           # Vista personalizada
├── ListadoMedicamentoActivity.kt    # Pantalla principal
└── RegistroMedicamentoActivity.kt   # Pantalla de registro/edición
```

## 🚀 Instalación

### Prerrequisitos

- Android Studio Hedgehog | 2023.1.1 o superior
- JDK 11 o superior
- Gradle 8.0+
- Dispositivo Android o Emulador con API 24+

### Pasos

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/Eduardo-VaZu/appMedicamento.git
   cd appMedicamento
   ```

2. **Abrir en Android Studio**
   - File → Open → Seleccionar la carpeta del proyecto

3. **Sincronizar Gradle**
   - Android Studio sincronizará automáticamente las dependencias

4. **Ejecutar la aplicación**
   - Conectar un dispositivo Android o iniciar un emulador
   - Click en el botón Run (▶️) o presionar `Shift + F10`

## 🌐 API

La aplicación consume la siguiente API REST:

- **Base URL**: `https://apimedicamento.onrender.com`
- **Endpoints**:
  - `GET /medicamento/list` - Listar todos los medicamentos
  - `GET /medicamento/{id}` - Obtener un medicamento por ID
  - `POST /medicamento/save` - Crear un nuevo medicamento
  - `PUT /medicamento/update` - Actualizar un medicamento
  - `DELETE /medicamento/delete/{id}` - Eliminar un medicamento

### Modelo de Datos

```json
{
  "codigo": 1,
  "nombre": "Paracetamol",
  "stock": 100,
  "precio": 5.50,
  "foto": "https://example.com/imagen.jpg"
}
```

## 📱 Pantallas

### 1. Listado de Medicamentos
- Muestra todos los medicamentos en un RecyclerView
- Botón "Nuevo" para agregar medicamentos
- Click en un item para editar

### 2. Registro/Edición
- Formulario con validación de campos
- Campos:
  - Nombre del medicamento
  - Stock (número entero)
  - Precio (número decimal)
  - URL de imagen
- Botones: Guardar, Eliminar (solo edición), Volver

## 🎨 Características de UI/UX

- **Material Design 3**: Uso de componentes modernos
- **Edge-to-Edge**: Aprovechamiento completo de la pantalla
- **ProgressBar**: Indicadores de carga durante peticiones HTTP
- **TextInputLayout**: Campos de texto con Material Design
- **AlertDialog**: Confirmación antes de eliminar
- **Toast**: Mensajes informativos
- **RecyclerView**: Lista eficiente y con scroll

## ⚙️ Configuración

### Cambiar URL de la API

Edita el archivo `ApiUtils.kt`:

```kotlin
companion object {
    val BASE_URL = "https://tu-api.com"
}
```

### Permisos

La app requiere los siguientes permisos (ya incluidos en `AndroidManifest.xml`):

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## 📄 Licencia

Este proyecto es de código abierto y está disponible bajo la [Licencia MIT](LICENSE).

## 👨‍💻 Autor

**Eduardo Vargas**
- GitHub: [@Eduardo-VaZu](https://github.com/Eduardo-VaZu)

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

⭐ Si te gusta este proyecto, considera darle una estrella en GitHub!
