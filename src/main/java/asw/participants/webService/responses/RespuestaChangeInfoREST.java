package asw.participants.webService.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ChangeInfoResponse")
public class RespuestaChangeInfoREST {

	private String participant;
	private String message;

	public RespuestaChangeInfoREST() {
	}

	public RespuestaChangeInfoREST(String participant, String message) {
		super();
		this.participant = participant;
		this.message = message;
	}

	public String getParticipant() {
		return participant;
	}

	@XmlElement
	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public String getMessage() {
		return message;
	}

	@XmlElement
	public void setMessage(String message) {
		this.message = message;
	}

}
