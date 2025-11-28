# CU04 - Enviar solicitud de Amistad

Actor: Usuario registrado

## Guión

1. Actor ingresa login del usuario remitente de la solicitud
2. Sistema verifica que exista un usuario con el login del remitente
3. Actor ingresa login del usuario destinatario de la solicitud
4. Sistema verifica que exista un usuario con el login del destinatario
5. Sistema verifica que el login del remitente sea diferente al login del destinatario
6. Sistema crea una nueva solicitud de amistad, con la fecha actual, el usuario remitente y el usuario destinatario

## Excepciones

**Excepción:** No existe un usuario con el login del remitente

2.1. Sistema muestra un mensaje "No existe un usuario con el  login del remitente"
2.2. Termina

**Excepción:** No existe un usuario con el login del destinatario

4.1. Sistema muestra un mensaje "No existe un usuario con el  login del destinatario"
4.2. Termina

**Excepción:** No se puede enviar una invitación a si mismo

5.1. Sistema muestra un mensaje "No se puede enviar una invitación a sí mismo"
5.2. Termina

<br>

---

> [Listado de casos de uso](../casos-de-uso.md)