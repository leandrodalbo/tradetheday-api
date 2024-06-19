CREATE TABLE trade (
    id                BIGSERIAL PRIMARY KEY NOT NULL,
    symbol            varchar(50) NOT NULL,
    volume            DECIMAL NOT NULL,
    price             DECIMAL NOT NULL,
    profitprice       DECIMAL NOT NULL,
    stopprice         DECIMAL NOT NULL,
    tradestatus       varchar(20) NOT NULL,
    ondatetime        BIGINT NOT NULL,
    traderesult       varchar(20),
    isakrakentrade    BOOLEAN,
    version           INTEGER NOT NULL
);