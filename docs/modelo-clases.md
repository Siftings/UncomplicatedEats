# Modelo de Clases

## Diagrama de Clases

```plantuml
@startuml
skinparam nodesep 200
skinparam linetype ortho

class Usuario {
  - login: String
  - nombre: String
  - password: String
}

class SolicitudAmistad {
  - id: long
  - fechaSolicitud: LocalDate
  - aceptado: Boolean
  - fechaRespuesta: LocalDate
}

Usuario "1" -right- "*" SolicitudAmistad : "remitente"
Usuario "1" -right- "*" SolicitudAmistad : "destinatario"

@enduml
```
