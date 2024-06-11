DROP TABLE IF EXISTS onehour;
CREATE TABLE onehour (
    symbol             varchar(50) PRIMARY KEY NOT NULL,
    binanceEntry      BOOLEAN NOT NULL,
    binancePrice      DECIMAL,
    binanceStop       DECIMAL,
    binanceProfit     DECIMAL,
    krakenEntry       BOOLEAN NOT NULL,
    krakenPrice       DECIMAL,
    krakenStop        DECIMAL,
    krakenProfit      DECIMAL,
    version            integer NOT NULL
);
