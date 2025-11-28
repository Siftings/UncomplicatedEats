# CU02 - Iniciar sesión

Actor: Usuario registrado

## Guión 

1. Actor ingresa login
2. Sistema verifica que exista un usuario con ese login
3. Actor ingresa password
4. Sistema verifica que el password coincida con el password de ese usuario
5. Sistema establece el usuario actual

## Excepciones

**Excepción:** No existe un usuario con ese login

2.1. Sistema muestra un mensaje "No existe un usuario con ese login"
2.2. Termina

**Excepción:** La contraseña no coincide

4.1. Sistema muestra un mensaje "La contraseña no coincide"
4.2. Termina

<br>

---

> [Listado de casos de uso](../casos-de-uso.md)