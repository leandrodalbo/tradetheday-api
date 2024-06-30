# Trade the day API

- Fetching and analysing data from different exchanges.
- The data is saved identifying possible trading opportunities.
- Using Kraken API to open market/stop-loss orders.
- Strategies used: Engulfing candles and MA crossovers.
- take-profit-orders (TODO)
- Schedule update check with latest timestamps (TODO)
- Notification notifications (TODO)


## API Documentation
- /tradetheday/swagger.html
- /tradetheday/docs


### Creating Postgres container
```bash
docker run  --name opentrades -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=opentrades -p 5432:5432  postgres:14.4
```

### Local Docker image
```bash
$ ./gradlew bootBuildImage
```


### Running with docker-compose
```bash
$ docker-compose -f ./dcompose/docker-compose.yml up
```

### Observability
Prometheus: http://localhost:9090/
Grafana: http://localhost:3000/explore
