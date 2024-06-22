# Trade the day API

- Fetching and analysing data from different exchanges.
- The data is saved identifying possible trading opportunities.
- Using Kraken API to open market/stop-loss orders.
- strategies used: Engulfing candles and MA crossovers.
- Slack notifications (TODO)


## API Documentation
- /opentrade/swagger.html
- /opentrade/docs


```bash
docker run  --name opentrades -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=opentrades -p 5432:5432  postgres:14.4
```
