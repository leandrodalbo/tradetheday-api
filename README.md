# Open Trade API

## API Documentation

- /opentrade/swagger.html
- /opentrade/docs

## Check Trading entries

- GET /opentrade/crypto/engulfing/{speed}

### Response

```json
[
  {
    "id": 0,
    "symbol": "BTCUSDT",
    "binancespeed": "HIGH",
    "binanceengulfing": true,
    "binanceprice": 78000.0,
    "binancestop": 77800.45,
    "binanceprofit": 79900.43,
    "krakenspeed": "HIGH",
    "krakenengulfing": false,
    "krakenprice": 0,
    "krakenstop": 0,
    "krakenprofit": 0,
    "version": 0
  }
]
```

## Open new trade

- POST /opentrade/crypto/kraken/neworder

### Request Body

```json
{
  "symbol": "string",
  "volume": 0,
  "profitprice": 0,
  "stopprice": 0
}
```

### Response

```json
{
  "id": 1,
  "symbol": "string",
  "volume": 0,
  "profitprice": 0,
  "stopprice": 0,
  "status": "OPEN",
  "ondatetime": 1718556594,
  "version": 0
}
```

## Check Trades by status

- GET /opentrade/crypto/trades/{status}'

### Response

```json
[
  {
    "id": 1,
    "symbol": "string",
    "volume": 0,
    "profitprice": 0,
    "stopprice": 0,
    "status": "OPEN",
    "ondatetime": 1718556594,
    "version": 0
  }
]
```

## Trading day summary

- todo

### Postgres db Container

```bash
docker run  --name opentrades -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=opentrades -p 5432:5432  postgres:14.4
```
