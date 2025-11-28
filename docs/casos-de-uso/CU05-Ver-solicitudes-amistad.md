# CU05 - Ver solicitudes de amistad

Actor: Usuario registrado

## Guión

1. Actor ingresa su login
2. Sistema verifica que exista un usuario con ese login
3. Sistema busca solicitudes de amistad que no hayan sido aceptadas donde el usuario sea destinatario
4. Sistema muestra login y nombre del remitente de las solicitudes de amistad encontradas

## Excepciones

**Excepcion:** No existe un usuario con ese login

2.1. Sistema muestra un mensaje "No existe otro usuario con ese login"
2.2. Termina

**Excepcion:** No se encuentran solicitudes de amistad sin aceptar

3.1. Sistema muestra un mensaje "No se tienen solicitudes de amistad sin aceptar"
3.2. Termina

<br>

---

> [Listado de casos de uso](../casos-de-uso.md)