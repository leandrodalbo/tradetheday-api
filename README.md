# Open Trade API

## API Documentation

- /opentrade/swagger.html
- /opentrade/docs

## GET trading entries request

- /opentrade/crypto/engulfing/{speed}

## GET trading entries response example

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

## Open and monitor orders (Kraken)

- todo

## Trading day summary

- todo

### Postgres db Container

```bash
docker run  --name opentrades -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=opentrades -p 5432:5432  postgres:14.4
```
