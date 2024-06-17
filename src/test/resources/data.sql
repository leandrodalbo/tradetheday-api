INSERT INTO opportunity(symbol,
                        binancespeed,
                        binanceengulfing,
                        binanceprice,
                        binancestop,
                        binanceprofit,
                        krakenspeed,
                        krakenengulfing,
                        krakenprice,
                        krakenstop,
                        krakenprofit,
                        ondatetime,
                        version)
VALUES('BTCUSDT','HIGH', TRUE, 2.0, 1.8, 2.2,
       'HIGH',TRUE, 2.0, 1.8, 2.2, 1718545121 ,0);

INSERT INTO opportunity(symbol,
                        binancespeed,
                        binanceengulfing,
                        binanceprice,
                        binancestop,
                        binanceprofit,
                        krakenspeed,
                        krakenengulfing,
                        krakenprice,
                        krakenstop,
                        krakenprofit,
                        ondatetime,
                        version)
VALUES('SOLUSD','LOW', FALSE, 2.0, 1.8, 2.2,
       'HIGH',TRUE, 2.0, 1.8, 2.2, 1718545122,0);

INSERT INTO trade (
           symbol,
           volume,
           profitprice,
           stopprice,
           tradestatus,
           ondatetime,
           traderesult,
           isakrakentrade,
           version
       )
VALUES('SOLUSD', 0.1, 1.8, 1.2,'OPEN',1718545122, 'FAILED',FALSE, 0);

INSERT INTO trade (
           symbol,
           volume,
           profitprice,
           stopprice,
           tradestatus,
           ondatetime,
           traderesult,
           isakrakentrade,
           version
       )
VALUES('SOLUSD', 0.1, 1.8, 1.2,'CLOSED',1718545122, 'SUCCESS',FALSE, 0);