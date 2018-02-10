package asw.agents.webService.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import asw.dbManagement.model.Agent;

@XmlRootElement(name = "Agent")
public class RespuestaInfoREST {

	private String name;
	private String ID;
	private String email;
	private String kind;
	private int kindCode;

	public RespuestaInfoREST() {
	}

	public RespuestaInfoREST(Agent agent) {
		setFirstName(agent.getName());
		setID(agent.getDNI());
		setEmail(agent.getEmail());
		setKind(agent.getKind());
		setKindCode(agent.getKindCode());
	}

	public String getFirstName() {
		return name;
	}

	@XmlElement
	public void setFirstName(String firstName) {
		this.name = firstName;
	}

	public String getID() {
		return ID;
	}

	@XmlElement
	public void setID(String iD) {
		ID = iD;
	}

	public String getEmail() {
		return email;
	}

	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}

	public String getKind() {
		return kind;
	}

	@XmlElement
	public void setKind(String kind) {
		this.kind = kind;
	}

	public int getKindCode() {
		return kindCode;
	}

	@XmlElement
	public void setKindCode(int kindCode) {
		this.kindCode = kindCode;
	}

}
