package asw.agents.webService.request;

public class PeticionChangeIdREST {

	private String ident;
	private String password;
	private String newIdent;

	public PeticionChangeIdREST() {

	}

	public PeticionChangeIdREST(String ident, String password, String newIdent) {
		super();
		this.ident = ident;
		this.password = password;
		this.newIdent = newIdent;
	}

	public String getEmail() {
		return ident;
	}

	public void setEmail(String email) {
		this.ident = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewEmail() {
		return newIdent;
	}

	public void setNewEmail(String newEmail) {
		this.newIdent = newEmail;
	}

}
