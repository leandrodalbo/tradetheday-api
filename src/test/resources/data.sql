INSERT INTO opportunity(symbolspeed,
                        binanceengulfing,
                        binanceprice,
                        binancestop,
                        binanceprofit,
                        krakenengulfing,
                        krakenprice,
                        krakenstop,
                        krakenprofit,
                        ondatetime,
                        version)
VALUES('BTCUSDT-HIGH', TRUE, 2.0, 1.8, 2.2, TRUE, 2.0, 1.8, 2.2, 1718545121 ,0);

INSERT INTO opportunity(symbolspeed,
                        binanceengulfing,
                        binanceprice,
                        binancestop,
                        binanceprofit,
                        krakenengulfing,
                        krakenprice,
                        krakenstop,
                        krakenprofit,
                        ondatetime,
                        version)
VALUES('SOLUSD-LOW', FALSE, 2.0, 1.8, 2.2, TRUE, 2.0, 1.8, 2.2, 1718545122,0);

INSERT INTO trade (
           symbol,
           volume,
           price,
           profitprice,
           stopprice,
           tradestatus,
           ondatetime,
           traderesult,
           isakrakentrade,
           version
       )
VALUES('SOLUSD', 0.1, 1.8, 1.8, 1.2,'OPEN',1710787703, 'FAILED',FALSE, 0);

INSERT INTO trade (
           symbol,
           volume,
           price,
           profitprice,
           stopprice,
           tradestatus,
           ondatetime,
           traderesult,
           isakrakentrade,
           version
       )
VALUES('SOLUSD', 0.1,1.5, 1.8, 1.2,'CLOSED',1718473703, 'SUCCESS',FALSE, 0);

INSERT INTO trade (
           symbol,
           volume,
           price,
           profitprice,
           stopprice,
           tradestatus,
           ondatetime,
           traderesult,
           isakrakentrade,
           version
       )
VALUES('SOLUSD', 0.1,1.5, 1.8, 1.2,'CLOSED',1705603703, null,FALSE, 0);