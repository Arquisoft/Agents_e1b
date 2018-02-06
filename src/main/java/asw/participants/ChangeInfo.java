package asw.participants;

import org.springframework.http.ResponseEntity;

import asw.participants.webService.request.PeticionChangeEmailREST;
import asw.participants.webService.request.PeticionChangePasswordREST;
import asw.participants.webService.responses.RespuestaChangeInfoREST;

public interface ChangeInfo {
	/**
	 * Cambio de contraseña
	 *
	 * @param datos requeridos (email, password, newPassword)
	 * @return 
	 */
	public ResponseEntity<RespuestaChangeInfoREST> changePassword(PeticionChangePasswordREST datos);

	/**
	 * Cambio de email
	 * 
	 * @param datos requeridos (email, password, newEmail)
	 * @return respuesta en xml o json
	 */
	public ResponseEntity<RespuestaChangeInfoREST> changeEmail(PeticionChangeEmailREST datos);
}
