package asw.agents.webService.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import asw.dbManagement.model.Agent;

@XmlRootElement(name = "agent")
public class RespuestaInfoREST {	
	
	
	private String name;
	private String ident;
	private String email;
	private String location;
	private String kind;
	private int kindCode;
	
	public RespuestaInfoREST() {}
	
	public RespuestaInfoREST(Agent agent){
		setName(agent.getName());
		setIdent(agent.getIdent());
		setEmail(agent.getEmail());
		setLocation(agent.getLocation());
		setKind(agent.getKind());
		setKindCode(agent.getKindCode());
	}
	
	//GETTERS
	public String getName() {
		return name;
	}
	
	public String getIdent() {
		return ident;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getKind() {
		return kind;
	}
	
	public int getKindCode() {
		return kindCode;
	}
	
	
	//SETTERS
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public void setIdent(String ident) {
		this.ident = ident;
	}
	
	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement
	public void setLocation(String location) {
		this.location = location;
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
