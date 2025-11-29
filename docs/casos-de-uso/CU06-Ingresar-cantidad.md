# CU06 - Ingresar cantidad de alimento

Actor: Usuario registrado

## Guión (Curso normal de eventos)

1. Actor visualiza alimento seleccionado con su información nutricional por 100g
2. Sistema muestra campo para ingresar cantidad en gramos
3. Actor ingresa cantidad deseada
4. Sistema valida que la cantidad sea positiva
5. Sistema calcula valores nutricionales proporcionales:
   - Calorías totales
   - Proteínas totales
   - Carbohidratos totales
   - Grasas totales
6. Sistema muestra preview con los valores calculados
7. Actor confirma la cantidad
8. Sistema asocia el alimento con la cantidad a la comida

## Excepciones (Caminos alternos)

**Excepción:** Cantidad es cero o negativa

4.1. Sistema muestra un mensaje "La cantidad debe ser mayor a cero"
4.2. Vuelve al paso 2

**Excepción:** Cantidad es demasiado grande (>10000g)

4.1. Sistema muestra un mensaje "La cantidad parece incorrecta, verifique"
4.2. Actor puede confirmar o modificar

**Excepción:** Actor cancela

7.1. Sistema no guarda el alimento
7.2. Termina

<br>

---

> [Listado de casos de uso](../casos-de-uso.md)
