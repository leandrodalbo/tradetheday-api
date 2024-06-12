DROP TABLE IF EXISTS opportunity;
CREATE TABLE opportunity (
    id                BIGSERIAL PRIMARY KEY NOT NULL,
    symbol            varchar(50) NOT NULL,
    binancetimeframe  varchar(20) NOT NULL,
    binanceentry      BOOLEAN NOT NULL,
    binanceprice      DECIMAL,
    binancestop       DECIMAL,
    binanceprofit     DECIMAL,
    krakentimeframe   INTEGER NOT NULL,
    krakenentry       BOOLEAN NOT NULL,
    krakenprice       DECIMAL,
    krakenstop        DECIMAL,
    krakenprofit      DECIMAL,
    version           INTEGER NOT NULL
);
