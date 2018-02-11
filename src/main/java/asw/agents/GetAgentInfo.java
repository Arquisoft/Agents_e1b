package asw.agents;

import org.springframework.http.ResponseEntity;

import asw.agents.webService.request.PeticionInfoREST;
import asw.agents.webService.responses.RespuestaInfoREST;

public interface GetAgentInfo {

	public ResponseEntity<RespuestaInfoREST> getPOSTpetition(PeticionInfoREST peticion);

}
