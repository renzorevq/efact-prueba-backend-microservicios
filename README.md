# Prueba Tecnica Efact Backend Microservicios

## Ejecucion del proyecto

### Levantar los servicios

```bash
docker compose up --build
```

La API estara disponible en:

```
http://localhost:5000
```

### Endpoints


| Metodo | Endpoint | Descripción                                      |
|--------|----------|--------------------------------------------------|
| GET    | `/documents` | Listar todos los documentos no eliminados        |
| GET    | `/documents/{idDocumento}` | Obtener un documento (incluye si esta eliminado) |
| POST   | `/documents` | Crear nuevo documento                            |
| PUT    | `/documents/{idDocumento}` | Actualizar documento                             |
| DELETE | `/documents/{idDocumento}` | Eliminar documento                               |
| POST | `/documents/verificar-validez` | Verificar la validez de la firma y el documento  |

## Ejemplo – Crear Documento

```json
{
  "idDocumento": "FACT-000000004",
  "rucEmisor": "12345678900",
  "rucReceptor": "12345678900",
  "subtotal": 10,
  "igv": 1.80,
  "total": 11.8,
  "items": [
    {
      "descripcion": "polo",
      "precio": 10,
      "cantidad": 1,
      "total": 11.8,
      "igv": 1.8
    }
  ]
}
```

## Ejemplo – Verificar Validez

```json
{
  "documento": {
    "fecha": "2026-02-12T13:55:50.968",
    "idDocumento": "FACT-000000001",
    "igv": 1.8,
    "items": [
      {
        "cantidad": 1.0,
        "descripcion": "polo",
        "igv": 1.8,
        "precio": 10.0,
        "total": 11.8
      }
    ],
    "rucEmisor": "12345678900",
    "rucReceptor": "12345678900",
    "subtotal": 10.0,
    "total": 11.8,
    "uuid": "e5bb9742-f3ec-4f91-9e1c-ee99a92d3978",
    "validacion": {
      "estado": "VALIDO",
      "fecha": "2026-02-12T13:55:51.285"
    }
  },
  "firma": "sBhKbwiN0JLTNK5itu6OQr8bkPVTHeukL77XJ2gKFA7KbFoLsbMVO9y0PBX9P9aobAF95lC/1iZ7yQdH"
}
```

