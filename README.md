# Park App

API simples de controle de estacionamento (check-in, check-out e cálculo de pagamento), construída com [Javalin](https://javalin.io/).

## Executando

```bash
mvn compile exec:java
```

A API sobe em `http://localhost:7070`.

## Rotas

### `GET /`

Verifica se a API está no ar.

**Resposta `200`**
```json
{
  "success": true,
  "message": "Running!",
  "data": null
}
```

---

### `POST /checkin`

Registra a entrada de um veículo no estacionamento.

**Payload**
| Campo          | Tipo               | Obrigatório | Observações                                                                 |
|----------------|--------------------|-------------|-------------------------------------------------------------------------------|
| `vehiclePlate` | `string`           | Sim         | Placa do veículo                                                              |
| `vehicleName`  | `string`           | Sim         | Nome/modelo do veículo (vazio ou com mais de 2 caracteres)                    |
| `vehicleType`  | `string` (enum)    | Sim         | Um de: `CAR`, `MOTORCYLE`, `TRUCK`, `PICKUP_TRUCK`, `BUS`, `MINIBUS`          |
| `startAt`      | `string` (datetime)| Não         | Data/hora de entrada (ISO-8601, ex: `2026-07-14T10:00:00`)                    |

**Exemplo**
```json
{
  "vehiclePlate": "ABC1D23",
  "vehicleName": "Civic",
  "vehicleType": "CAR",
  "startAt": "2026-07-14T10:00:00"
}
```

**Respostas**
- `200` — Check-in realizado:
```json
{
  "success": true,
  "message": "Entrada do veículo Civic com a placa ABC1D23 foi feito",
  "data": null
}
```
- `400` — Payload inválido (campo obrigatório ausente/inválido) ou veículo já estacionado (`VehicleParkedException`).

---

### `PUT /checkout/{vehicle_plate}`

Registra a saída de um veículo e calcula o valor a ser pago.

**Parâmetro de rota**
| Parâmetro       | Tipo     | Obrigatório | Observações            |
|-----------------|----------|-------------|-------------------------|
| `vehicle_plate` | `string` | Sim         | Placa do veículo (na URL) |

Não há payload no corpo da requisição.

**Respostas**
- `200` — Check-out realizado, com o valor total (em reais):
```json
{
  "success": true,
  "message": "Veículo de saída",
  "data": {
    "price": 15
  }
}
```
- `404` — Veículo não encontrado no estacionamento (`VehicleNotFoundException`).

---

## Formato de erro de validação

Quando o payload de `/checkin` falha na validação, a API responde `400` com um mapa de erros do Javalin, por exemplo:

```json
{
  "vehiclePlate": [
    {
      "message": "A placa não foi informada",
      ...
    }
  ]
}
```
