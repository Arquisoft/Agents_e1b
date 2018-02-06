package asw.participants.util;

import asw.dbManagement.model.Participant;
import asw.participants.factory.ErrorFactory;
import asw.participants.factory.ErrorFactory.Errors;

public class Assert {

	/**
	 * 
	 * @param email
	 * @return excepcion si esta vacio
	 */
	public static boolean isEmailEmpty(String email) {
		if(email.trim().isEmpty())
			throw ErrorFactory.getError(Errors.REQUIRED_EMAIL);
		else
			return false;
	}
	
	public static boolean isPasswordEmpty(String password) {
		if(password.trim().isEmpty())
			throw ErrorFactory.getError(Errors.REQUIRED_PASSWORD);
		else
			return false;
	}

	/**
	 * Comprobacion de si el correo es valido
	 * @param email
	 * @return true si es valido.
	 */
	public static boolean isEmailValid(String email) {
		String[] mailSplit = email.split("@");
		if (mailSplit.length != 2) {
			throw ErrorFactory.getError(Errors.WRONG_EMAIL_STYLE);
		}
		mailSplit = email.split("\\.");
		if (mailSplit.length != 2 || mailSplit[0].length() == 0 || mailSplit[1].length() == 0) {
			throw ErrorFactory.getError(Errors.WRONG_EMAIL_STYLE);
		}
		return true;
	}
	
	/**
	 * 
	 * @param participant
	 * @return devuelve false si no es null o excepcion
	 */
	public static boolean isParticipantNull(Participant participant){
		if (participant == null) {
			throw ErrorFactory.getError(Errors.USER_NOT_FOUND);
		}
		return false;		
	}
	
	public static boolean isPasswordCorrect(String password,Participant participant){
		if (!password.equals(participant.getPassword())) {
			throw ErrorFactory.getError(Errors.INCORRECT_PASSWORD_DO_NOT_MATCH);
		}
		return true;
	}
	
	public static boolean isSamePassword(String password, String password2){
		if (password.equals(password2)) {
			throw ErrorFactory.getError(Errors.INCORRECT_PASSWORD);
		}
		return true;
	}

	public static boolean isSameEmail(String email, String email2){
		if (email.equals(email2)) {
			throw ErrorFactory.getError(Errors.SAME_EMAIL);
		}
		return true;
	}
}
