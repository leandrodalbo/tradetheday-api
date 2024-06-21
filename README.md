# Open Trade API

## API Documentation

- /opentrade/swagger.html
- /opentrade/docs

## Check Trading entries

- GET /opentrade/crypto/engulfing/entries

## Open new trade

- POST /opentrade/crypto/kraken/neworder

### Request Body

```json
{
  "symbol": "string",
  "volume": 0,
  "price": 0,
  "profitprice": 0,
  "stopprice": 0
}
```

## Trades Search
- GET /opentrade/crypto/trades?status=OPEN&result=SUCCESS&today=false'

### Postgres db Container

```bash
docker run  --name opentrades -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=opentrades -p 5432:5432  postgres:14.4
```
