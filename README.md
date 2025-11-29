# UncomplicatedEats - Aplicación de Seguimiento Nutricional

> Aplicación web para registro y seguimiento de alimentación diaria, cálculo de calorías y macronutrientes

---

## Características

- Registro e inicio de sesión de usuarios
- Categorías de comidas predeterminadas (Desayuno, Almuerzo, Cena, Snacks, etc.)
- Base de datos con 25+ alimentos predefinidos
- Registro de comidas con múltiples alimentos
- Cálculo automático de calorías y macronutrientes
- Resumen diario con totales y progreso visual
- Edición y eliminación de comidas registradas
- Búsqueda de alimentos por nombre

## Arquitectura

### Tecnologías
- **Backend**: Spring Boot 3.5.7
- **Frontend**: Vaadin 24.9.4
- **Base de datos**: H2 (en memoria)
- **ORM**: Hibernate/JPA
- **Testing**: JUnit 5, Mockito
- **Java**: 25

### Estructura del Proyecto
```
redsocial/
├── src/main/java/com/ingesoft/redsocial/
│   ├── modelo/          # Entidades JPA
│   │   ├── Usuario.java
│   │   ├── CategoriaComida.java
│   │   ├── Comida.java
│   │   ├── Alimento.java
│   │   └── AlimentoComida.java
│   ├── repositorios/    # Spring Data JPA
│   ├── servicios/       # Lógica de negocio
│   ├── vistas/          # Interfaces Vaadin
│   └── seguridad/       # Gestión de sesión
└── docs/                # Documentación y casos de uso
```

## Instrucciones de Uso

### Compilar el proyecto
```bash
cd redsocial
mvn clean package -DskipTests
```

### Ejecutar la aplicación
```bash
mvn spring-boot:run -DskipTests
```

### Ejecutar pruebas
```bash
mvn test
```

### Acceder a la aplicación
- **URL**: http://localhost:8080
<!-- En la maquina de codespaces es %github.dev/h2-console/login.jsp -->
- **Consola H2**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Usuario: `sa`
  - Password: (vacío)

## Uso de la Aplicación

### 1. Registro de Usuario
1. Accede a http://localhost:8080
2. Haz clic en "Registrarse"
3. Completa el formulario con tu información
4. Haz clic en "Registrarse"

### 2. Iniciar Sesión
1. Ingresa tu usuario y contraseña
2. Haz clic en "Ingresar"

### 3. Agregar una Comida
1. En el resumen diario, haz clic en "➕ Agregar Comida"
2. Selecciona una categoría (Desayuno, Almuerzo, etc.)
3. Opcionalmente ingresa un nombre personalizado
4. Haz clic en "Crear Comida"
5. Busca y selecciona alimentos de la lista
6. Ingresa la cantidad en gramos
7. Haz clic en "Agregar Alimento" (puedes agregar múltiples)
8. Cuando termines, haz clic en "Terminar y Ver Resumen"

### 4. Ver Resumen Diario
- El resumen muestra:
  - Total de calorías consumidas vs objetivo
  - Barra de progreso visual
  - Macronutrientes (proteínas, carbohidratos, grasas)
  - Comidas agrupadas por categoría
- Puedes cambiar la fecha para ver otros días

### 5. Editar o Eliminar Comidas
- Haz clic en el botón "✏️" para editar
- Haz clic en el botón "🗑️" para eliminar

## Especificaciones Técnicas

### Modelo de Datos
- **Usuario**: Información del usuario y objetivo calórico
- **CategoriaComida**: Categorías predefinidas (Desayuno, Almuerzo, etc.)
- **Comida**: Registro de una comida con fecha/hora y totales
- **Alimento**: Base de datos de alimentos con información nutricional
- **AlimentoComida**: Relación entre alimentos y comidas con cantidades

### Casos de Uso Implementados
- [CU01](docs/casos-de-uso/CU01-Registrar-nuevo-usuario.md) - Registrar nuevo usuario
- [CU02](docs/casos-de-uso/CU02-Iniciar-sesion.md) - Iniciar sesión
- [CU03](docs/casos-de-uso/CU03-Crear-categorias.md) - Crear categorías de comidas
- [CU04](docs/casos-de-uso/CU04-Agregar-comida.md) - Agregar comida nueva
- [CU05](docs/casos-de-uso/CU05-Seleccionar-alimentos.md) - Seleccionar alimentos para comida
- [CU06](docs/casos-de-uso/CU06-Ingresar-cantidad.md) - Ingresar cantidad de alimento
- [CU07](docs/casos-de-uso/CU07-Editar-comida.md) - Editar comida registrada
- [CU08](docs/casos-de-uso/CU08-Eliminar-comida.md) - Eliminar comida registrada
- [CU09](docs/casos-de-uso/CU09-Resumen-diario.md) - Ver resumen diario

Ver documentación completa en:
- [Modelo de Clases](docs/modelo-clases-fitness.md)
- [Casos de Uso](docs/casos-de-uso-fitness.md)

## Configuración

### Objetivo Calórico
Por defecto, cada usuario nuevo tiene un objetivo de 2000 kcal/día. Puedes modificarlo en la base de datos.

### Alimentos Predefinidos
La aplicación incluye 25 alimentos comunes con su información nutricional. Se cargan automáticamente al iniciar.

### Categorías Predefinidas
Se crean automáticamente 6 categorías:
1. Desayuno
2. Media mañana
3. Almuerzo
4. Merienda
5. Cena
6. Snacks

## Notas para Desarrolladores

### Agregar Nuevos Alimentos
Edita el método `crearAlimentosDefault()` en `AlimentoService.java`

### Modificar Categorías
Edita el método `crearCategoriasDefault()` en `CategoriaComidaService.java`

### Crear Nuevas Vistas
Las vistas Vaadin deben:
1. Extender `VerticalLayout` (o similar)
2. Usar la anotación `@Route("ruta")`
3. Implementar `BeforeEnterObserver` para validar sesión

### Estructura de Base de Datos
La BD H2 se reinicia cada vez que se detiene la aplicación (configurada en memoria). Para persistencia, modifica `application.properties`.

---

## Licencia

Este proyecto es de uso educativo.

