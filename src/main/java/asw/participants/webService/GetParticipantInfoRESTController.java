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
import asw.dbManagement.model.Participant;
import asw.participants.GetParticipantInfo;
import asw.participants.util.Assert;
import asw.participants.webService.request.PeticionInfoREST;
import asw.participants.webService.responses.RespuestaInfoREST;
import asw.participants.webService.responses.errors.ErrorResponse;

@RestController
public class GetParticipantInfoRESTController implements GetParticipantInfo {

	@Autowired
	private GetParticipant getParticipant;

	@Override
	@RequestMapping(value = "/user", method = RequestMethod.POST, headers = { "Accept=application/json",
			"Accept=application/xml" }, produces = { "application/json", "text/xml" })
	public ResponseEntity<RespuestaInfoREST> getPOSTpetition(@RequestBody(required = true) PeticionInfoREST peticion) {

		Assert.isEmailEmpty(peticion.getLogin());
		Assert.isEmailValid(peticion.getLogin());
		Assert.isPasswordEmpty(peticion.getPassword());

		Participant participant = getParticipant.getParticipant(peticion.getLogin());

		Assert.isParticipantNull(participant);

		Assert.isPasswordCorrect(peticion.getPassword(), participant);

		/*
		 * Añadimos la información al modelo, para que se muestre en la pagina
		 * html: datosParticipant
		 */

		return new ResponseEntity<RespuestaInfoREST>(new RespuestaInfoREST(participant), HttpStatus.OK);
	}

	@ExceptionHandler(ErrorResponse.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponses(ErrorResponse error) {
		return error.getMessageJSONFormat();
	}
}
