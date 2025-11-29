# CU05 - Seleccionar alimentos para comida

Actor: Usuario registrado

## Guión (Curso normal de eventos)

1. Actor busca alimentos por nombre (opcional)
2. Sistema muestra lista de alimentos disponibles
3. Actor selecciona uno o varios alimentos de la lista
4. Sistema verifica que cada alimento existe
5. Para cada alimento seleccionado:
   5.1. Sistema solicita cantidad en gramos
   5.2. Actor ingresa cantidad
   5.3. Sistema calcula valores nutricionales:
        - Calorías = (caloriasPor100g * cantidad) / 100
        - Proteínas = (proteinasPor100g * cantidad) / 100
        - Carbohidratos = (carbohidratosPor100g * cantidad) / 100
        - Grasas = (grasasPor100g * cantidad) / 100
   5.4. Sistema crea registro AlimentoComida
6. Sistema actualiza totales de la comida
7. Sistema muestra resumen de la comida con todos los alimentos

## Excepciones (Caminos alternos)

**Excepción:** No existe el alimento seleccionado

4.1. Sistema muestra un mensaje "El alimento seleccionado no existe"
4.2. Termina

**Excepción:** Cantidad inválida (negativa o cero)

5.2.1. Sistema muestra un mensaje "Debe ingresar una cantidad válida"
5.2.2. Vuelve al paso 5.1

**Excepción:** No se seleccionaron alimentos

3.1. Sistema muestra un mensaje "Debe seleccionar al menos un alimento"
3.2. Termina

<br>

---

> [Listado de casos de uso](../casos-de-uso.md)
