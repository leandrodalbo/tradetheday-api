# Open Trade API

## API Documentation

- /opentrade/swagger.html
- /opentrade/docs

## Check Trading entries

- GET /opentrade/crypto/engulfing/entries

### Response

```json
[
  {
    "symbolspeed": "ZGBPZUSD-HIGH",
    "binanceengulfing": false,
    "binanceprice": 0,
    "binancestop": 0,
    "binanceprofit": 0,
    "krakenengulfing": true,
    "krakenprice": 1.26996,
    "krakenstop": 1.24456,
    "krakenprofit": 1.29536,
    "ondatetime": 1718716501,
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

## Trades Search

- GET /opentrade/crypto/trades?status=OPEN&result=SUCCESS&today=false'

### Response

```json
[
  {
    "id": 0,
    "symbol": "string",
    "volume": 0,
    "profitprice": 0,
    "stopprice": 0,
    "tradestatus": "OPEN",
    "ondatetime": 0,
    "traderesult": "SUCCESS",
    "isakrakentrade": true,
    "version": 0
  }
]
```

### Postgres db Container

```bash
docker run  --name opentrades -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=opentrades -p 5432:5432  postgres:14.4
```
