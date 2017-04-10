package stockhistory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class StockHistorySerializer extends StdSerializer<StockHistory>
{
    public StockHistorySerializer(Class<StockHistory> t)
    {
        super(t);
    }

    public StockHistorySerializer() {
        this(null);
    }

    @Override
    public void serialize(
            StockHistory value, JsonGenerator jgen, SerializerProvider provider
            ) throws IOException, JsonProcessingException
    {
        jgen.writeStartObject();
        jgen.writeStringField("quotes", value.getQuotes().replaceAll("\\\\",""));
        jgen.writeEndObject();
    }
}
