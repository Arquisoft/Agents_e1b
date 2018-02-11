package asw.agents.webService.request;

public class PeticionInfoREST {

	private String ident;
	private String password;
	private String kind;

	public PeticionInfoREST() {

	}
	
	public PeticionInfoREST(String ident,String password){
		this.ident = ident;
		this.password = password;
	}
	
	public PeticionInfoREST(String ident,String password, String kind){
		this.ident = ident;
		this.password = password;
		this.kind = kind;
	}
	
	
	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
	
	public void setident(String ident) {
		this.ident = ident;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getident() {
		return ident;
	}

	public String getPassword() {
		return password;
	}

}
