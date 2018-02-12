package asw.agents.util;

import asw.agents.factory.ErrorFactory;
import asw.agents.factory.ErrorFactory.Errors;
import asw.dbManagement.model.Agent;
import org.apache.commons.validator.routines.EmailValidator;

// Nota para el futuro:
// Creo que las excepciones no deberian de ser arrojadas desde aqui, aqui simplemente deberian
// estar declarados una serie de predicados para devolver un booleano

public class Assert {

	/**
	 * 
	 * @param email
	 * @return excepcion si esta vacio
	 */
	public static boolean isEmailEmpty(String email) {
		if (email.trim().isEmpty())
			throw ErrorFactory.getError(Errors.REQUIRED_EMAIL);
		else
			return false;
	}

	public static boolean isPasswordEmpty(String password) {
		if (password.trim().isEmpty())
			throw ErrorFactory.getError(Errors.REQUIRED_PASSWORD);
		else
			return false;
	}

	public static boolean isIdentEmpty(String ident) {
		if (ident.trim().isEmpty())
			throw ErrorFactory.getError(Errors.REQUIRED_IDENT);
		else
			return false;
	}

	public static boolean isKindEmpty(Integer kind) {
		if (kind == null) {
			throw ErrorFactory.getError(Errors.REQUIRED_KIND);
		} else
			return false;

	}

	/**
	 * Comprobacion de si el correo es valido
	 * 
	 * @param email
	 * @return true si es valido.
	 */
	public static boolean isEmailValid(String email) {
		boolean allowLocal = true;
		if (!EmailValidator.getInstance(allowLocal).isValid(email))
			throw ErrorFactory.getError(Errors.WRONG_EMAIL_STYLE);
		else
			return true;
	}

	/**
	 * 
	 * @param participant
	 * @return devuelve false si no es null o excepcion
	 */
	public static boolean isParticipantNull(Agent participant) {
		if (participant == null) {
			throw ErrorFactory.getError(Errors.USER_NOT_FOUND);
		}
		return false;
	}

	public static boolean isPasswordCorrect(String password, Agent participant) {
		if (!password.equals(participant.getPassword())) {
			throw ErrorFactory.getError(Errors.INCORRECT_PASSWORD_DO_NOT_MATCH);
		}
		return true;
	}

	public static boolean isKindCodeCorrect(int kindCode, Agent participant) {
		if (!(kindCode == participant.getKindCode())) {
			throw ErrorFactory.getError(Errors.INCORRECT_KINDCODE);
		}
		return true;
	}

	public static boolean isSamePassword(String password, String password2) {
		if (password.equals(password2)) {
			throw ErrorFactory.getError(Errors.INCORRECT_PASSWORD);
		}
		return true;
	}

	public static boolean isSameEmail(String email, String email2) {
		if (email.equals(email2)) {
			throw ErrorFactory.getError(Errors.SAME_EMAIL);
		}
		return true;
	}

	public static boolean isSameIdent(String ident, String ident2) {
		if (ident.equals(ident2)) {
			throw ErrorFactory.getError(Errors.SAME_IDENT);
		}
		return true;
	}

	public static boolean isIdValid(String ident) {
		if (!ident.matches("[a-zA-Z0-9]*")) {
			throw ErrorFactory.getError(Errors.WRONG_ID_STYLE);
		}
		return true;

	}

	public static boolean isKindCorrect(int kind) {
		if (kind < 1 || kind > 3) {
			throw ErrorFactory.getError(Errors.WRONG_KIND_STYLE);
		}
		return true;
	}

}
