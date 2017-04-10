package stockhistory;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConvertToJSON
{

    public static String getHistoryForSymbol(String symbol)
    {
        if (symbol == null || symbol.equals("")) throw new ResourceNotFoundException();

        String url = urlPrefix();
        url += "s=" + symbol.trim().toUpperCase();
        url += timeString() + suffix();

        String resultString = JavaUrlConnectionReader.getUrlContents(url);
        List<AvgQuote>  quoteList = jacksonConvertAvg(symbol.trim().toUpperCase(),resultString);
        return buildJsonArray(quoteList);
    }

    private static String timeString()
    {
        final long thirtyDays= 30;

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDate currentDate = currentTime.toLocalDate();
        LocalDate thirtyDaysAgo = currentDate.minusDays(thirtyDays);


        String a = decorateForYahoo(zeroPaddedString(checkLessThanZeroMonth(thirtyDaysAgo)),"a");
        String b = decorateForYahoo(zeroPaddedString(thirtyDaysAgo.getDayOfMonth()),"b");
        String c = decorateForYahoo(String.format("%d", thirtyDaysAgo.getYear()),"c");
        String d = decorateForYahoo(zeroPaddedString(checkLessThanZeroMonth(currentDate)),"d");
        String e = decorateForYahoo(zeroPaddedString(currentDate.getDayOfMonth()),"e");
        String f = decorateForYahoo(String.format("%d", currentDate.getYear()),"f");

        return (a+b+c+d+e+f);
    }

    private static String urlPrefix() { return "http://ichart.finance.yahoo.com/table.csv?";}
    private static String suffix() { return "&g=d&ignore.csv";}

    private static int checkLessThanZeroMonth(LocalDate value)
    {
        if (value.getMonthValue() - 1 == 0)
        {
            return 12;
        }
        else
        {
            return value.getMonthValue() -1 ;
        }
    }

    private static String decorateForYahoo(String value, String yahooField)
    {
        return String.format("&%s=%s", yahooField, value);

    }

    private static String zeroPaddedString(int value)
    {
        if(value > 9)
        {
            return String.format("0%d",  value);
        }
        else
        {
            return String.format("%d", value);
        }
    }


    private static List<DayQuote> jacksonConvert(String symbol, String bigCsvString)
    {
       List<DayQuote> quoteList = new ArrayList<DayQuote>();

        String[] lines = bigCsvString.split("\n");
        String[] header = lines[0].split(",");
        int numHeaders = header.length;

        for(int i=1; i < lines.length; i++)
        {
            String data[] = lines[i].split(",");

            String date = data[0];
            double open = convertDouble(data[1]);
            double high = convertDouble(data[2]);
            double low = convertDouble(data[3]);
            double close = convertDouble(data[4]);
            long volume = convertLong(data[5]);
            double adjClose = convertDouble(data[6]);

            quoteList.add(new DayQuote(symbol, date, open, high, low, close, volume, adjClose));
        }

        Collections.reverse(quoteList); //Put in date ascending order
        return quoteList;
    }

    private static double average(double open,double high, double low, double close)
    {
        double sum = open + high + low + close;
        if (sum > 0.0)
        {
            return sum/4.0;
        }
        else
        {
            return 0.0;
        }
    }

    private static List<AvgQuote> jacksonConvertAvg(String symbol, String bigCsvString)
    {
        List<AvgQuote> quoteList = new ArrayList<AvgQuote>();

        String[] lines = bigCsvString.split("\n");
        String[] header = lines[0].split(",");
        int numHeaders = header.length;

        for(int i=1; i < lines.length; i++)
        {
            String data[] = lines[i].split(",");

            String date = data[0];
            double open = convertDouble(data[1]);
            double high = convertDouble(data[2]);
            double low = convertDouble(data[3]);
            double close = convertDouble(data[4]);
            long volume = convertLong(data[5]);
            double adjClose = convertDouble(data[6]);

            double avg = average(open,high,low,close);

            quoteList.add(new AvgQuote(symbol, date, avg, volume, adjClose));
        }

        Collections.reverse(quoteList); //Put in date ascending order
        return quoteList;
    }


    private static String buildJsonArray(List<AvgQuote> quoteList)
    {
        ObjectMapper mapper = new ObjectMapper();
        String s;

        try {
            s = mapper.writeValueAsString(quoteList);
            s.replaceAll("\\\\",""); //Get rid of any remaining escaped strings.
        }
        catch(JsonProcessingException jpe)
        {
            throw new ResourceNotFoundException();
        }
        return s;
    }

    private static double convertDouble(String s)
    {
        double value = 0.0;
        try{
            value = Double.parseDouble(s);
        }
        catch(NumberFormatException nfe){
            // just return 0.0
        }
        return value;
    }

    private static long convertLong(String s)
    {
        long value = 0;
        try{
            value = Long.parseLong(s);
        }
        catch(NumberFormatException nfe){
            // just return 0
        }
        return value;
    }

}
