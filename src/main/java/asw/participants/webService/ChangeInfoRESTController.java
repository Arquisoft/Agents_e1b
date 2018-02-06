package asw.participants.webService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import asw.dbManagement.GetParticipant;
import asw.dbManagement.UpdateInfo;
import asw.dbManagement.model.Participant;
import asw.participants.ChangeInfo;
import asw.participants.util.Assert;
import asw.participants.webService.request.PeticionChangeEmailREST;
import asw.participants.webService.request.PeticionChangePasswordREST;
import asw.participants.webService.responses.RespuestaChangeInfoREST;
import asw.participants.webService.responses.errors.ErrorResponse;

@RestController
public class ChangeInfoRESTController implements ChangeInfo {

	@Autowired
	private GetParticipant getParticipant;
	@Autowired
	private UpdateInfo updateInfo;

	@Override
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST, headers = { "Accept=application/json",
			"Accept=application/xml" }, produces = { "application/json", "text/xml" })
	public ResponseEntity<RespuestaChangeInfoREST> changePassword(@RequestBody(required=true) PeticionChangePasswordREST datos) {
		String email = datos.getEmail();
		String password = datos.getPassword();
		String newPassword = datos.getNewPassword();
		
		Assert.isEmailEmpty(email);
		Assert.isEmailValid(email);
		
		Assert.isPasswordEmpty(password);
		Assert.isPasswordEmpty(newPassword);
		
		Assert.isSamePassword(password, newPassword);	

		Participant p = getParticipant.getParticipant(email);
		Assert.isParticipantNull(p);
		Assert.isPasswordCorrect(password, p);

		updateInfo.updatePassword(p, password, newPassword);

		RespuestaChangeInfoREST res = new RespuestaChangeInfoREST(email, "contraseña actualizada correctamente");
		return new ResponseEntity<RespuestaChangeInfoREST>(res, HttpStatus.OK);
	}

	@Override
	@RequestMapping(value = "/changeEmail", method = RequestMethod.POST, headers = { "Accept=application/json",
			"Accept=application/xml" }, produces = { "application/json", "text/xml" })
	public ResponseEntity<RespuestaChangeInfoREST> changeEmail(@RequestBody(required = true) PeticionChangeEmailREST datos) {
		String email = datos.getEmail();
		String password = datos.getPassword();
		String nuevoEmail = datos.getNewEmail();
		
		Assert.isEmailEmpty(email);
		Assert.isEmailValid(email);
		
		Assert.isEmailEmpty(nuevoEmail);
		Assert.isEmailValid(nuevoEmail);
		
		Assert.isSameEmail(email, nuevoEmail);

		Assert.isPasswordEmpty(password);
		
		Participant p = getParticipant.getParticipant(email);
		Assert.isParticipantNull(p);
		Assert.isPasswordCorrect(password, p);
		
		updateInfo.updateEmail(p, nuevoEmail);

		RespuestaChangeInfoREST res = new RespuestaChangeInfoREST(nuevoEmail, "email actualizado correctamente");
		return new ResponseEntity<RespuestaChangeInfoREST>(res, HttpStatus.OK);
	}

	@ExceptionHandler(ErrorResponse.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponses(ErrorResponse error) {
		return error.getMessageJSONFormat();
	}

}
