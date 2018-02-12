package asw.dbManagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import asw.agents.util.CsvReader;

@Entity
@Table(name = "Participant")
public class Agent {

	// Id generado automáticamente para diferenciar cada uno (para mapear)
	@Id
	@GeneratedValue
	private Long id;

	// Atributos del participante
	private String name;
	private String password;
	@Column(unique = true)
	private String email;
	private String ident;
	private String location;
	private int kindCode;

	/**
	 * Constructor vacío (ya que es para mapear)
	 */
	Agent() {
	}

	public Agent(String name, String password, String email, String ident, String location, int kindCode) {
		super();
		this.name = name;
		this.password = password;
		this.email = email;
		this.ident = ident;
		this.location = location;
		this.kindCode = kindCode;
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	protected void setKindCode(int kindCode) {
		this.kindCode = kindCode;
	}

	public int getKindCode() {
		return kindCode;
	}

	public String getKind() {
		return new CsvReader().buscarTipo(getKindCode());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agent other = (Agent) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Agent [name=" + name + ", location=" + location + ", email=" + email + ", ident=" + ident + ", kind=" + getKind()
				+ ", kindCode=" + kindCode + "]";
	}

}
