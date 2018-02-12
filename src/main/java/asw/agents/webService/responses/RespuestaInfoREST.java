package asw.agents.webService.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import asw.dbManagement.model.Agent;

@XmlRootElement(name = "agent")
public class RespuestaInfoREST {

	private String name;
	private String location;
	private String email;
	private String id;
	private String kind;
	private int kindCode;

	public RespuestaInfoREST() {
	}

	public RespuestaInfoREST(Agent agent) {
		setName(agent.getName());
		setLocation(agent.getLocation());
		setEmail(agent.getEmail());
		setIdent(agent.getIdent());
		setKind(agent.getKind());
		setKindCode(agent.getKindCode());
	}

	// GETTERS
	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public String getEmail() {
		return email;
	}

	public String getId() {
		return id;
	}

	public String getKind() {
		return kind;
	}

	public int getKindCode() {
		return kindCode;
	}

	// SETTERS
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public void setLocation(String location) {
		this.location = location;
	}

	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement
	public void setIdent(String id) {
		this.id = id;
	}

	@XmlElement
	public void setKind(String kind) {
		this.kind = kind;
	}

	@XmlElement
	public void setKindCode(int kindCode) {
		this.kindCode = kindCode;
	}

}
