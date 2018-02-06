package asw.participants.webService.responses.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="User password is required")
public class RequiredPasswordErrorResponse extends ErrorResponse {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessageJSONFormat() {
		// TODO Auto-generated method stub
		return "{\"reason\": \"User password is required\"}";
	}

	@Override
	public String getMessageStringFormat() {
		// TODO Auto-generated method stub
		return "User password is required";
	}

}
