# CU07 - Editar comida registrada

Actor: Usuario registrado

## Guión (Curso normal de eventos)

1. Actor selecciona una comida existente del resumen diario
2. Sistema verifica que la comida existe y pertenece al usuario
3. Sistema muestra detalle de la comida con todos sus alimentos
4. Actor puede:
   - Modificar nombre de la comida
   - Cambiar categoría
   - Agregar más alimentos (CU05)
   - Modificar cantidad de alimentos existentes (CU06)
   - Eliminar alimentos de la comida
5. Sistema recalcula totales después de cada cambio
6. Actor confirma cambios
7. Sistema actualiza la comida en la base de datos
8. Sistema muestra mensaje de confirmación

## Excepciones (Caminos alternos)

**Excepción:** La comida no existe

2.1. Sistema muestra un mensaje "La comida no existe"
2.2. Termina

**Excepción:** La comida no pertenece al usuario

2.1. Sistema muestra un mensaje "No tiene permiso para editar esta comida"
2.2. Termina

**Excepción:** Actor cancela edición

6.1. Sistema descarta cambios
6.2. Termina

**Excepción:** Se eliminan todos los alimentos

4.1. Sistema pregunta "¿Desea eliminar la comida completa?"
4.2. Si acepta, ejecuta CU08
4.3. Si cancela, vuelve al paso 4

<br>

---

> [Listado de casos de uso](../casos-de-uso.md)
