package stockhistory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Stock symbol problem.")
public class ResourceNotFoundException extends RuntimeException
{
}
