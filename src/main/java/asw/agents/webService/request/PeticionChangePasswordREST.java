package asw.agents.webService.request;

public class PeticionChangePasswordREST {

	private String email;
	private String password;
	private String newPassword;
	private String ident;
	

	public PeticionChangePasswordREST() {

	}

	public PeticionChangePasswordREST(String email, String password, String newPassword) {
		super();
		this.password = password;
		this.newPassword = newPassword;
		this.email = email;
	}
	
	public PeticionChangePasswordREST(String email, String password, String newPassword, String ident) {
		super();
		this.password = password;
		this.newPassword = newPassword;
		this.email = email;
		this.ident = ident;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getNewPassword() {
		return newPassword;
	}
	
	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

}
