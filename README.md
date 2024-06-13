# Crypto
 
## find trading opportunities by fetching OHLC candles (Binance/Kraken) 
## open and monitor orders (Kraken) 
## trading day summary 

### New logic could be added to use other exchanges or stocks

### Postgres Container
```bash
docker run  --name opentrades -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=opentrades -p 5432:5432  postgres:14.4
```
