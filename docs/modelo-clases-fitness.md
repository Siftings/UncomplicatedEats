# Modelo de Clases - App de Fitness y Alimentación

## Diagrama de Clases

```mermaid
classDiagram
    class Usuario {
        -String login
        -String nombre
        -String password
        -Double pesoActual
        -Double altura
        -Integer objetivoCalorias
        +List~Comida~ comidas
    }
    
    class CategoriaComida {
        -Long id
        -String nombre
        -String descripcion
        -Integer orden
        +List~Comida~ comidas
    }
    
    class Comida {
        -Long id
        -String nombre
        -LocalDateTime fechaHora
        -Integer totalCalorias
        -Double totalProteinas
        -Double totalCarbohidratos
        -Double totalGrasas
        +Usuario usuario
        +CategoriaComida categoria
        +List~AlimentoComida~ alimentos
    }
    
    class Alimento {
        -Long id
        -String nombre
        -String marca
        -Integer caloriasPor100g
        -Double proteinasPor100g
        -Double carbohidratosPor100g
        -Double grasasPor100g
        -Boolean esGenerico
        +List~AlimentoComida~ alimentosComida
    }
    
    class AlimentoComida {
        -Long id
        -Integer cantidadGramos
        -Integer caloriasCalculadas
        -Double proteinasCalculadas
        -Double carbohidratosCalculados
        -Double grasasCalculadas
        +Comida comida
        +Alimento alimento
    }
    
    Usuario "1" --> "*" Comida : registra
    CategoriaComida "1" --> "*" Comida : agrupa
    Comida "1" --> "*" AlimentoComida : contiene
    Alimento "1" --> "*" AlimentoComida : usado en
```

## Descripción de Entidades

### Usuario
Representa un usuario de la aplicación con sus datos personales y objetivos nutricionales.

### CategoriaComida
Categorías predeterminadas para agrupar comidas (Desayuno, Almuerzo, Cena, Snacks, etc.)

### Comida
Representa una comida registrada por el usuario en un momento específico. Agrupa varios alimentos y calcula totales.

### Alimento
Base de datos de alimentos con su información nutricional por cada 100g.

### AlimentoComida
Tabla intermedia que relaciona alimentos con comidas, incluyendo la cantidad específica consumida y los valores nutricionales calculados.
