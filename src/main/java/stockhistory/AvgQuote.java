package stockhistory;


public class AvgQuote
{
    private String symbol;
    private String date;
    private double avg;
    private long volume;
    private double adjClose;


    public AvgQuote(String symbol, String date, double avg, long volume, double adjClose)
    {

        this.symbol = symbol;
        this.date = date;
        this.avg = avg;
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

    public double getAvg()
    {
        return avg;
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
