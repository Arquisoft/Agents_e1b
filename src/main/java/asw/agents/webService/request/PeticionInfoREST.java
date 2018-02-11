package asw.agents.webService.request;

public class PeticionInfoREST {

	private String login;
	private String password;
	private int kind;

	public PeticionInfoREST() {

	}
	
	public PeticionInfoREST(String login,String password, int kind){
		this.login = login;
		this.password = password;
		this.kind = kind;
	}
	
	
	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

}
