package asw.agents.webService.responses.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Password Incorrect")
public class IncorrectKindCodeErrorResponse extends ErrorResponse {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessageJSONFormat() {
		return "{\"reason\": \"Kind Incorrect\"}";
	}

	@Override
	public String getMessageStringFormat() {
		return "Kind Incorrect";
	}

}
