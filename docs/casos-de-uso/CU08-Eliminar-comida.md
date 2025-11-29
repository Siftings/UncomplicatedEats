# CU08 - Eliminar comida registrada

Actor: Usuario registrado

## Guión (Curso normal de eventos)

1. Actor selecciona una comida existente
2. Sistema verifica que la comida existe y pertenece al usuario
3. Actor selecciona opción "Eliminar comida"
4. Sistema muestra diálogo de confirmación con:
   - Nombre de la comida
   - Categoría
   - Total de calorías
   - Cantidad de alimentos
5. Actor confirma la eliminación
6. Sistema elimina:
   - Todos los registros AlimentoComida asociados
   - La comida
7. Sistema actualiza el resumen diario
8. Sistema muestra mensaje "Comida eliminada correctamente"

## Excepciones (Caminos alternos)

**Excepción:** La comida no existe

2.1. Sistema muestra un mensaje "La comida no existe"
2.2. Termina

**Excepción:** La comida no pertenece al usuario

2.1. Sistema muestra un mensaje "No tiene permiso para eliminar esta comida"
2.2. Termina

**Excepción:** Actor cancela eliminación

5.1. Sistema no elimina la comida
5.2. Vuelve a mostrar el detalle de la comida

<br>

---

> [Listado de casos de uso](../casos-de-uso.md)
