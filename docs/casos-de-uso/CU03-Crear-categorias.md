# CU03 - Crear categorías de comidas

Actor: Sistema (automático al inicializar)

## Guión (Curso normal de eventos)

1. Sistema verifica si existen categorías en la base de datos
2. Si no existen, sistema crea categorías predeterminadas:
   - Desayuno (orden: 1)
   - Media mañana (orden: 2)
   - Almuerzo (orden: 3)
   - Merienda (orden: 4)
   - Cena (orden: 5)
   - Snacks (orden: 6)
3. Sistema guarda las categorías en la base de datos

## Excepciones (Caminos alternos)

**Excepción:** Ya existen categorías

2.1. Sistema no crea categorías duplicadas
2.2. Termina

<br>

---

> [Listado de casos de uso](../casos-de-uso-fitness.md)
