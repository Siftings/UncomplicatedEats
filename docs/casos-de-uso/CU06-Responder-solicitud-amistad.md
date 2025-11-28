# CU06 - Responder solicitud de amistad

Actor: Usuario registrado

## Guión

1. Actor ingresa su login
2. Sistema verifica que exista un usuario con ese login
3. Actor ingresa id de la solicitud
4. Sistema verifica que exista una solicitud con ese id
5. Sistema verifica que la solicitud tenga al usuario como destinatario
6. Actor ingresa respuesta, si acepta o no la solicitud
7. Sistema actualiza la solicitud de amistad colocando la respuesta y la fecha actual como fecha de respuesta

## Excepciones

**Excepcion:** No existe un usuario con ese login

2.1. Sistema muestra un mensaje "No existe otro usuario con ese login"
2.2. Termina

**Excepcion:** No existe esa solicitud de amistad

4.1. Sistema muestra un mensaje "No existe esa solicitud de amistad"
4.2. Termina

**Excepcion:** La solicitud no tiene al usuario como destinatario

5.1. Sistema muestra un mensaje "La solicitud no tiene al usuario como destinatario"
5.2. Termina

<br>

---

> [Listado de casos de uso](../casos-de-uso.md)