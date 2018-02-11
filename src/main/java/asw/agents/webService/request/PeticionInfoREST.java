package asw.agents.webService.request;

import asw.agents.util.CsvReader;

public class PeticionInfoREST {

	private String login;
	private String password;
	private int kindCode;
	private String kind;

	public PeticionInfoREST() {

	}
	
	public PeticionInfoREST(String login,String password, int kindCode){
		this.login = login;
		this.password = password;
		this.kindCode = kindCode;
	}
	
	
	public String getKind() {
		CsvReader csvReader = new CsvReader();
		setKind(csvReader.buscarTipo(getKindCode()));
		return kind;
	}

	public void setKind(String kind) {
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

	public int getKindCode() {
		return kindCode;
	}

	public void setKindCode(int kindCode) {
		this.kindCode = kindCode;
	}

}
