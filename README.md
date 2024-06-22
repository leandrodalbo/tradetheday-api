# Trade the day API

- Fetching and analysing data from different exchanges.
- The data is saved identifying possible trading opportunities.
- Using Kraken API to open market/stop-loss orders.
- Strategies used: Engulfing candles and MA crossovers.
- Slack notifications (TODO)


## API Documentation
- /tradetheday/swagger.html
- /tradetheday/docs


```bash
docker run  --name opentrades -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=opentrades -p 5432:5432  postgres:14.4
```
