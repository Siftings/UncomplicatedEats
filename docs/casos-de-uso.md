# Casos de Uso - Uncomplicated Eats

## Diagrama de Casos de Uso

```plantuml
@startuml
left to right direction
skin rose

actor Visitante
actor Usuario 

rectangle "Uncomplicated Eats" {

    Visitante --> (Registrar nuevo usuario)
    Visitante --> (Iniciar sesión)

    Usuario --> (Crear categorías de comidas)
    Usuario --> (Agregar comida nueva)
    Usuario --> (Seleccionar alimentos para comida)
    Usuario --> (Ingresar cantidad de alimento)
    Usuario --> (Editar comida registrada)
    Usuario --> (Eliminar comida registrada)
     Usuario --> (Ver resumen diario)

}
@enduml
```
## Listado de Casos de Uso

| #    | Nombre | Estado |
|------|--------|--------|
| CU01 | [Registrar nuevo usuario](casos-de-uso/CU01-Registrar-nuevo-usuario.md) |  Adaptado |
| CU02 | [Iniciar sesión](casos-de-uso/CU02-Iniciar-sesion.md) |  Adaptado |
| CU03 | [Crear categorías de comidas](casos-de-uso/CU03-Crear-categorias.md) | Nuevo |
| CU04 | [Agregar comida nueva](casos-de-uso/CU04-Agregar-comida.md) | Nuevo |
| CU05 | [Seleccionar alimentos para comida](casos-de-uso/CU05-Seleccionar-alimentos.md) | Nuevo |
| CU06 | [Ingresar cantidad de alimento](casos-de-uso/CU06-Ingresar-cantidad.md) | Nuevo |
| CU07 | [Editar comida registrada](casos-de-uso/CU07-Editar-comida.md) | Nuevo |
| CU08 | [Eliminar comida registrada](casos-de-uso/CU08-Eliminar-comida.md) | Nuevo |
| CU09 | [Ver resumen diario](casos-de-uso/CU09-Resumen-diario.md) | Nuevo |

## Flujo Principal de la Aplicación

```mermaid
sequenceDiagram
    participant U as Usuario
    participant S as Sistema
    participant BD as Base de Datos
    
    U->>S: Inicia sesión
    S->>BD: Valida credenciales
    BD-->>S: Usuario válido
    S-->>U: Muestra resumen diario
    
    U->>S: Selecciona "Agregar comida"
    S-->>U: Muestra categorías disponibles
    
    U->>S: Selecciona categoría (ej: Desayuno)
    S-->>U: Muestra lista de alimentos
    
    U->>S: Selecciona alimentos
    S-->>U: Solicita cantidades
    
    U->>S: Ingresa cantidades (gramos)
    S->>S: Calcula valores nutricionales
    S->>BD: Guarda comida
    BD-->>S: Confirmación
    S-->>U: Muestra comida registrada
    
    U->>S: Ver resumen diario
    S->>BD: Obtiene comidas del día
    BD-->>S: Lista de comidas
    S->>S: Calcula totales por categoría
    S-->>U: Muestra resumen agrupado
```
