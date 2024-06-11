CREATE TABLE trade_one_hour (
    symbol             varchar(50) PRIMARY KEY NOT NULL,
    binance_entry      BOOLEAN NOT NULL,
    binance_price      DECIMAL,
    binance_stop       DECIMAL,
    binance_profit     DECIMAL,
    kraken_entry       BOOLEAN NOT NULL,
    kraken_price       DECIMAL,
    kraken_stop        DECIMAL,
    kraken_profit      DECIMAL,
    version            integer NOT NULL
);

CREATE TABLE trade_four_hour (
    symbol             varchar(50) PRIMARY KEY NOT NULL,
    binance_entry      BOOLEAN NOT NULL,
    binance_price      DECIMAL,
    binance_stop       DECIMAL,
    binance_profit     DECIMAL,
    kraken_entry       BOOLEAN NOT NULL,
    kraken_price       DECIMAL,
    kraken_stop        DECIMAL,
    kraken_profit      DECIMAL,
    version            integer NOT NULL
);

CREATE TABLE trade_one_day (
    symbol             varchar(50) PRIMARY KEY NOT NULL,
    binance_entry      BOOLEAN NOT NULL,
    binance_price      DECIMAL,
    binance_stop       DECIMAL,
    binance_profit     DECIMAL,
    kraken_entry       BOOLEAN NOT NULL,
    kraken_price       DECIMAL,
    kraken_stop        DECIMAL,
    kraken_profit      DECIMAL,
    version            integer NOT NULL
);