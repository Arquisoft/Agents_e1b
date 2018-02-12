package asw.agents.webService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import asw.agents.GetAgentInfo;
import asw.agents.util.Assert;
import asw.agents.webService.request.PeticionInfoREST;
import asw.agents.webService.responses.RespuestaInfoREST;
import asw.agents.webService.responses.errors.ErrorResponse;
import asw.dbManagement.GetAgent;
import asw.dbManagement.model.Agent;

@RestController
public class GetAgentInfoRESTController implements GetAgentInfo {

	@Autowired
	private GetAgent getAgent;

	@Override
	@RequestMapping(value = "/user", method = RequestMethod.POST, headers = { "Accept=application/json",
			"Accept=application/xml" }, produces = { "application/json", "text/xml" })
	public ResponseEntity<RespuestaInfoREST> getPOSTpetition(@RequestBody(required = true) PeticionInfoREST peticion) {

		// Datos de inicio de sesion
		Assert.isIdentEmpty(peticion.getident());
		Assert.isPasswordEmpty(peticion.getPassword());
		Assert.isKindEmpty(peticion.getKind());

		// Comprobaciones
		Agent agent = getAgent.getAgent(peticion.getident());
		Assert.isKindCorrect(peticion.getKind());
		Assert.isIdValid(peticion.getident());
		Assert.isParticipantNull(agent);
		Assert.isPasswordCorrect(peticion.getPassword(), agent);
		Assert.isKindCodeCorrect(peticion.getKind(), agent);

		/*
		 * Añadimos la información al modelo, para que se muestre en la pagina html:
		 * (datosAgente|agentData).html
		 */

		return new ResponseEntity<RespuestaInfoREST>(new RespuestaInfoREST(agent), HttpStatus.OK);
	}

	@ExceptionHandler(ErrorResponse.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponses(ErrorResponse error) {
		return error.getMessageJSONFormat();
	}
}
