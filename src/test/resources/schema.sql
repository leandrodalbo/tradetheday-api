DROP TABLE IF EXISTS opportunity;
CREATE TABLE opportunity (
    symbolspeed       varchar(50) PRIMARY KEY NOT NULL,
    binanceengulfing  BOOLEAN NOT NULL,
    binanceprice      DECIMAL,
    binancestop       DECIMAL,
    binanceprofit     DECIMAL,
    krakenengulfing   BOOLEAN NOT NULL,
    krakenprice       DECIMAL,
    krakenstop        DECIMAL,
    krakenprofit      DECIMAL,
    ondatetime        BIGINT NOT NULL,
    version           INTEGER NOT NULL
);

DROP TABLE IF EXISTS trade;
CREATE TABLE trade (
    id                BIGSERIAL PRIMARY KEY NOT NULL,
    symbol            varchar(50) NOT NULL,
    volume            DECIMAL NOT NULL,
    profitprice       DECIMAL NOT NULL,
    stopprice         DECIMAL NOT NULL,
    tradestatus       varchar(20) NOT NULL,
    ondatetime        BIGINT NOT NULL,
    traderesult       varchar(20),
    isakrakentrade    BOOLEAN,
    version           INTEGER NOT NULL
);