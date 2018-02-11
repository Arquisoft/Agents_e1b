package asw.agents.webService.request;

public class PeticionChangeEmailREST {
	
	private String ident;
	private String email;
	private String password;
	private String newEmail;
	
	public PeticionChangeEmailREST() {

	}

	public PeticionChangeEmailREST(String email, String password, String newEmail) {
		super();
		this.email = email;
		this.password = password;
		this.newEmail = newEmail;
	}

	public PeticionChangeEmailREST(String ident, String email, String password, String newEmail) {
		this.ident = ident;
		this.email = email;
		this.password = password;
		this.newEmail = newEmail;
	}
	
	
	public String getIdent() {
		return ident;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

}
