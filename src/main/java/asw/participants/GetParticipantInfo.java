package asw.participants;

import org.springframework.http.ResponseEntity;

import asw.participants.webService.request.PeticionInfoREST;
import asw.participants.webService.responses.RespuestaInfoREST;

public interface GetParticipantInfo {

	public ResponseEntity<RespuestaInfoREST> getPOSTpetition(PeticionInfoREST peticion);

}
