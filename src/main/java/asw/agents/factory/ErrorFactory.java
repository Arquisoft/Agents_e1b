package asw.agents.factory;

import asw.agents.webService.responses.errors.ErrorResponse;
import asw.agents.webService.responses.errors.IncorrectKindCodeErrorResponse;
import asw.agents.webService.responses.errors.IncorrectPasswordErrorResponse;
import asw.agents.webService.responses.errors.PasswordDoNotMatchErrorResponse;
import asw.agents.webService.responses.errors.RequiredEmailErrorResponse;
import asw.agents.webService.responses.errors.RequiredIdentErrorResponse;
import asw.agents.webService.responses.errors.RequiredKindErrorResponse;
import asw.agents.webService.responses.errors.RequiredPasswordErrorResponse;
import asw.agents.webService.responses.errors.SameEmailErrorResponse;
import asw.agents.webService.responses.errors.SameIdentErrorResponse;
import asw.agents.webService.responses.errors.UnknownErrorResponse;
import asw.agents.webService.responses.errors.UserNotFoundResponse;
import asw.agents.webService.responses.errors.WrongEmailStyleErrorResponse;
import asw.agents.webService.responses.errors.WrongIdStyleErrorResponse;
import asw.agents.webService.responses.errors.WrongKindStyleErrorResponse;

//

//Creacion de los distintos tipos de error.
public class ErrorFactory {

	public static enum Errors {
		INCORRECT_PASSWORD, REQUIRED_EMAIL, REQUIRED_PASSWORD, REQUIRED_IDENT, REQUIRED_KIND, USER_NOT_FOUND, WRONG_EMAIL_STYLE, WRONG_ID_STYLE, WRONG_KIND_STYLE, INCORRECT_PASSWORD_DO_NOT_MATCH, SAME_EMAIL, SAME_IDENT, INCORRECT_KINDCODE,
	}

	// Generar Constructor privado no queremos que se pueda tener varias
	// instancias de la clase.
	private ErrorFactory() {
	}

	public static ErrorResponse getError(Errors error) {
		switch (error) {
		case INCORRECT_PASSWORD:
			return new IncorrectPasswordErrorResponse();
		case INCORRECT_KINDCODE:
			return new IncorrectKindCodeErrorResponse();
		case REQUIRED_EMAIL:
			return new RequiredEmailErrorResponse();
		case REQUIRED_PASSWORD:
			return new RequiredPasswordErrorResponse();
		case REQUIRED_IDENT:
			return new RequiredIdentErrorResponse();
		case REQUIRED_KIND:
			return new RequiredKindErrorResponse();
		case USER_NOT_FOUND:
			return new UserNotFoundResponse();
		case WRONG_EMAIL_STYLE:
			return new WrongEmailStyleErrorResponse();
		case WRONG_ID_STYLE:
			return new WrongIdStyleErrorResponse();
		case WRONG_KIND_STYLE:
			return new WrongKindStyleErrorResponse();
		case INCORRECT_PASSWORD_DO_NOT_MATCH:
			return new PasswordDoNotMatchErrorResponse();
		case SAME_EMAIL:
			return new SameEmailErrorResponse();
		case SAME_IDENT:
			return new SameIdentErrorResponse();
		default:// en caso de no conocer el error.
			return new UnknownErrorResponse();
		}
	}

}
