package asw.participants.webService.request;

public class PeticionChangePasswordREST {

	private String email;
	private String password;
	private String newPassword;
	
	public PeticionChangePasswordREST() {

	}

	public PeticionChangePasswordREST(String email, String password, String newPassword) {
		super();
		this.password = password;
		this.newPassword = newPassword;
		this.email = email;
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

}
