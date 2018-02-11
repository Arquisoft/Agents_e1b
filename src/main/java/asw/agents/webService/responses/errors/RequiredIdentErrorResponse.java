package asw.agents.webService.responses.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Users need an identificator")

public class RequiredIdentErrorResponse extends ErrorResponse{

	@Override
	public String getMessageJSONFormat() {
		return "{\"reason\": \"Users need an identificator \"}"; 
	}

	@Override
	public String getMessageStringFormat() {
		return "Users need an identificator";
	}

}


