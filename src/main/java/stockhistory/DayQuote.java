package stockhistory;

public class DayQuote
{
    private String symbol;
    private String date;
    private double open;
    private double high;
    private double low;
    private double close;
    private long volume;
    private double adjClose;

    public DayQuote(String symbol, String date, double open, double high, double low, double close,
                    long volume, double adjClose)
    {
        this.symbol = symbol;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.adjClose = adjClose;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public String getDate()
    {
        return date;
    }

    public double getOpen()
    {
        return open;
    }

    public double getHigh()
    {
        return high;
    }

    public double getLow()
    {
        return low;
    }

    public double getClose()
    {
        return close;
    }

    public long getVolume()
    {
        return volume;
    }

    public double getAdjClose()
    {
        return adjClose;
    }
}
