package asw.agents.webService.responses.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User idents are the same")

public class SameIdentErrorResponse extends ErrorResponse {

	@Override
	public String getMessageJSONFormat() {
		return "{\"reason\": \"User idents are the same\"}";
	}

	@Override
	public String getMessageStringFormat() {
		return "User idents are the same";
	}

}
