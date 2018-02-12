package asw.agents.webService.request;

public class PeticionInfoREST {

	private String ident;
	private String password;
	private Integer kind;

	public PeticionInfoREST() {

	}

	public PeticionInfoREST(String ident, String password) {
		this.ident = ident;
		this.password = password;
	}

	public PeticionInfoREST(String ident, String password, Integer kind) {
		this.ident = ident;
		this.password = password;
		this.kind = kind;
	}

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
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
