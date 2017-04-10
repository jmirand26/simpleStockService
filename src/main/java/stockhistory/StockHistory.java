package stockhistory;


import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

//@JsonSerialize(using = StockHistorySerializer.class)
public class StockHistory
{
    @JsonRawValue
    private final String quotes;

    @JsonRawValue
    public String getQuotes() {return quotes;}


    public StockHistory(String content)
    {
        this.quotes = content;
    }
}
