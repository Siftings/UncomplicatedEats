# CU01 - Registrar nuevo usuario

Actor : Visitante

## Guión (Curso normal de eventos)

1. Actor ingresa su login
2. Sistema verifica que no exista otro usuario con ese login
3. Actor ingresa su nombre
4. Actor ingresa su password
5. Sistema valida que el password tenga más de 5 letras
6. Sistema crea el nuevo usuario

## Excepciones (Caminos alternos)

**Excepcion:** Ya exista otro usuario con ese login

2.1. Sistema muestra un mensaje "Ya existe otro usuario con ese login"
2.2. Termina

**Excepction:** El password no cumple con las reglas

5.1. Sistema muestra un mensaje "La contraseña no cumple con la política de la red social"
5.2. Termina


<br>

---

> [Listado de casos de uso](../casos-de-uso.md)