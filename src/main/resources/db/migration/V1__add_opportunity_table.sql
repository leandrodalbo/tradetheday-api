CREATE TABLE opportunity (
    id                BIGSERIAL PRIMARY KEY NOT NULL,
    symbol            varchar(50) UNIQUE NOT NULL,
    binancespeed      varchar(20) NOT NULL,
    binanceengulfing  BOOLEAN NOT NULL,
    binanceprice      DECIMAL,
    binancestop       DECIMAL,
    binanceprofit     DECIMAL,
    krakenspeed       varchar(20) NOT NULL,
    krakenengulfing   BOOLEAN NOT NULL,
    krakenprice       DECIMAL,
    krakenstop        DECIMAL,
    krakenprofit      DECIMAL,
    version           INTEGER NOT NULL
);