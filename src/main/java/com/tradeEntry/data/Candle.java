package com.tradeEntry.data;

public record Candle(
        Float open,
        Float high,
        Float low,
        Float close,
        Long volume
) {

    public static Candle of(Float open,
              Float high,
              Float low,
              Float close,
              Long volume) {
        return new Candle(open, high, low, close, volume);

    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Candle c))
            return false;

        return (this.open.equals(c.open) && this.close.equals(c.close) && this.high.equals(c.high) && this.low.equals(c.low) && this.volume.equals(c.volume));
    }
}
