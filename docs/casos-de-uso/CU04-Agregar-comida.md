# CU04 - Agregar comida nueva

Actor: Usuario registrado

## Guión (Curso normal de eventos)

1. Actor selecciona opción "Agregar comida"
2. Sistema muestra lista de categorías disponibles
3. Actor selecciona una categoría (ej: Desayuno, Almuerzo, Cena)
4. Sistema verifica que la categoría existe
5. Actor ingresa nombre para la comida (opcional)
6. Sistema crea una nueva comida con:
   - Usuario actual
   - Categoría seleccionada
   - Fecha y hora actual
   - Nombre (si fue ingresado)
7. Sistema muestra pantalla para agregar alimentos a la comida
8. Sistema redirige a CU05 (Seleccionar alimentos)

## Excepciones (Caminos alternos)

**Excepción:** No existe la categoría seleccionada

4.1. Sistema muestra un mensaje "La categoría seleccionada no existe"
4.2. Termina

**Excepción:** Usuario no ha iniciado sesión

1.1. Sistema muestra un mensaje "Debe iniciar sesión"
1.2. Redirige a CU02 (Iniciar sesión)

<br>

---

> [Listado de casos de uso](../casos-de-uso-fitness.md)
