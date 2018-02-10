package asw.agents.webService.responses.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User kind is required")
public class RequiredKindErrorResponse extends ErrorResponse{

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessageJSONFormat() {
		return "{\"reason\": \"User kind is required\"}";
	}

	@Override
	public String getMessageStringFormat() {
		return "User kind is required";
	}

}
