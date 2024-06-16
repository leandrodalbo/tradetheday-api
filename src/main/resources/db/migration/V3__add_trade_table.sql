CREATE TABLE trade (
    id                BIGSERIAL PRIMARY KEY NOT NULL,
    symbol            varchar(50) NOT NULL,
    volume            DECIMAL NOT NULL,
    profitprice       DECIMAL NOT NULL,
    stopprice         DECIMAL NOT NULL,
    status            varchar(20) NOT NULL,
    ondatetime        BIGINT NOT NULL,
    version           INTEGER NOT NULL
);