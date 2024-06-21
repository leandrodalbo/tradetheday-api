# Open Trade API

## API Documentation

- /opentrade/swagger.html
- /opentrade/docs

## Check Trading entries

- GET /opentrade/crypto/engulfing/entries

## Set Kraken stop-loss order

- POST /opentrade/crypto/kraken/stop

### Request Body

```json
{
  "symbol": "string",
  "volume": 0,
  "trigger": 0
}
```
## Set Kraken market order

- POST /opentrade/crypto/kraken/enter

### Request Body

```json
{
  "symbol": "string",
  "volume": 0
}
```

### Postgres db Container

```bash
docker run  --name opentrades -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=opentrades -p 5432:5432  postgres:14.4
```
