# CU09 - Ver resumen diario

Actor: Usuario registrado

## Guión (Curso normal de eventos)

1. Actor accede a la pantalla principal o selecciona "Resumen diario"
2. Sistema obtiene la fecha actual (o fecha seleccionada)
3. Sistema busca todas las comidas del usuario para esa fecha
4. Sistema agrupa las comidas por categoría (Desayuno, Almuerzo, Cena, etc.)
5. Para cada categoría, sistema calcula:
   - Total de calorías
   - Total de proteínas
   - Total de carbohidratos
   - Total de grasas
   - Cantidad de comidas
6. Sistema calcula totales generales del día
7. Sistema calcula porcentaje respecto al objetivo diario (si está configurado)
8. Sistema muestra:
   - Resumen general con totales
   - Progreso visual (barra o gráfico)
   - Comidas agrupadas por categoría
   - Detalle de cada comida (nombre, hora, calorías)
9. Actor puede:
   - Ver detalle de una comida específica
   - Agregar nueva comida (CU04)
   - Editar comida existente (CU07)
   - Eliminar comida (CU08)
   - Cambiar fecha para ver otros días

## Excepciones (Caminos alternos)

**Excepción:** No hay comidas registradas para el día

3.1. Sistema muestra mensaje "No hay comidas registradas para hoy"
3.2. Sistema muestra botón "Agregar primera comida"
3.3. Termina

**Excepción:** Usuario no tiene objetivo configurado

7.1. Sistema no muestra porcentaje ni progreso
7.2. Sistema muestra solo valores absolutos

## Ejemplo de visualización

```
=== RESUMEN DEL DÍA - 28/11/2025 ===

📊 TOTALES DEL DÍA
Calorías: 1,850 / 2,000 kcal (92%)
Proteínas: 120g | Carbohidratos: 200g | Grasas: 65g

---

🌅 DESAYUNO (450 kcal)
  • Avena con frutas - 08:00 - 350 kcal
  • Café con leche - 08:15 - 100 kcal

🍽️ ALMUERZO (700 kcal)
  • Pollo con arroz - 13:30 - 700 kcal

🌙 CENA (500 kcal)
  • Ensalada y atún - 20:00 - 500 kcal

🍿 SNACKS (200 kcal)
  • Frutas - 16:00 - 200 kcal

---
[+ Agregar comida]
```

<br>

---

> [Listado de casos de uso](../casos-de-uso.md)
