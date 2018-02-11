package asw.agents.webService.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ChangeInfoResponse")
public class RespuestaChangeInfoREST {

	private String agent;
	private String message;

	public RespuestaChangeInfoREST() {
	}

	public RespuestaChangeInfoREST(String agent, String message) {
		super();
		this.agent = agent;
		this.message = message;
	}

	public String getAgent() {
		return agent;
	}

	@XmlElement
	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getMessage() {
		return message;
	}

	@XmlElement
	public void setMessage(String message) {
		this.message = message;
	}

}
