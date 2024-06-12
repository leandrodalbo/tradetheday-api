CREATE TABLE opportunity (
    id                BIGSERIAL PRIMARY KEY NOT NULL,
    symbol            varchar(50) NOT NULL,
    binancetimeframe  varchar(20) NOT NULL,
    binanceengulfing  BOOLEAN NOT NULL,
    binanceprice      DECIMAL,
    binancestop       DECIMAL,
    binanceprofit     DECIMAL,
    krakentimeframe   INTEGER NOT NULL,
    krakenengulfing   BOOLEAN NOT NULL,
    krakenprice       DECIMAL,
    krakenstop        DECIMAL,
    krakenprofit      DECIMAL,
    version           INTEGER NOT NULL
);