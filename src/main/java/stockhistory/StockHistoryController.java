package stockhistory;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class StockHistoryController
{

    @RequestMapping(method= RequestMethod.GET, value="/stockhistory/{symbol}",produces = MediaType.APPLICATION_JSON_VALUE)
    public StockHistory history(@PathVariable("symbol") String symbol) throws ResourceNotFoundException
    {
        String resultJSON = ConvertToJSON.getHistoryForSymbol(symbol);
        return new StockHistory(resultJSON);
    }

}
