package test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import asw.Application;
import asw.agents.webService.request.PeticionChangeEmailREST;
import asw.agents.webService.request.PeticionChangePasswordREST;
import asw.agents.webService.request.PeticionInfoREST;
import asw.dbManagement.GetAgent;
import asw.dbManagement.model.Agent;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({ "server.port=0" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainTest {

	@Value("${local.server.port}")
	private int port;

	private URL base;
	private RestTemplate template;

	@Autowired
	private GetAgent getAgent;

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
		template = new TestRestTemplate();
	}

	@Test
	public void T1domainModelEqualsTest() {
		Agent agent1 = getAgent.getAgent("12345678P");
		Agent agent2 = getAgent.getAgent("12345678A");
		Agent agent3 = getAgent.getAgent("12345678P");
		Agent agent4 = getAgent.getAgent("sensor1");
		assertFalse(agent1.equals(agent2));
		assertFalse(agent1.equals(4));
		assertTrue(agent1.equals(agent3));
		assertTrue(agent1.equals(agent1));
		assertFalse(agent1.equals(agent4));
	}

	@Test
	public void T2domainModelToString() {
		Agent agent1 = getAgent.getAgent("12345678P");
		assertEquals(agent1.toString(),
				"Agent [name=" + agent1.getName() + ", location=" + agent1.getLocation() + ", email="
						+ agent1.getEmail() + ", ident=" + agent1.getIdent() + ", kind=" + agent1.getKind()
						+ ", kindCode=" + agent1.getKindCode() + "]");
	}

	@Test
	public void T3domainModelHashCodeTest() {
		Agent agent1 = getAgent.getAgent("12345678P");
		Agent agent3 = getAgent.getAgent("12345678P");
		assertEquals(agent1.hashCode(), agent3.hashCode());
	}

	@Test
	public void T4agentExistAndCorrectPasssword() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";

		// PERSON
		response = template.postForEntity(userURI, new PeticionInfoREST("12345678P", "123456", 1), String.class);
		assertThat(response.getBody(), equalTo(
				"{\"name\":\"Paco\",\"location\":\"43.5479621,-5.9304147\",\"email\":\"paco@hotmail.com\",\"id\":\"12345678P\",\"kind\":\"Person\",\"kindCode\":1}"));

		// ENTITY
		response = template.postForEntity(userURI, new PeticionInfoREST("entidad1", "123456", 2), String.class);
		assertThat(response.getBody(), equalTo(
				"{\"name\":\"Valgrande Pajares\",\"location\":\"43.5479621,-5.9304147\",\"email\":\"pajares17@hotmail.com\",\"id\":\"entidad1\",\"kind\":\"Entity\",\"kindCode\":2}"));

		// SENSOR
		response = template.postForEntity(userURI, new PeticionInfoREST("sensor1", "123456", 3), String.class);
		assertThat(response.getBody(), equalTo(
				"{\"name\":\"SensorTemperatura\",\"location\":\"43.5479621,-5.9304147\",\"email\":\"sensorTTIS@hotmail.com\",\"id\":\"sensor1\",\"kind\":\"Sensor\",\"kindCode\":3}"));
	}

	@Test
	public void T5agentDoNotExist() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String userNotFound = "{\"reason\": \"User not found\"}";

		response = template.postForEntity(userURI, new PeticionInfoREST("sensor33", "ajksdkje", 1), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));

		response = template.postForEntity(userURI, new PeticionInfoREST("persona69", "shcxhqw", 2), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));
	}

	@Test
	public void T6incorrectPassword() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String incorrectPassword = "{\"reason\": \"Password do not match\"}";

		response = template.postForEntity(userURI, new PeticionInfoREST("12345678P", "12356", 1), String.class);
		assertThat(response.getBody(), equalTo(incorrectPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("12345678A", "12346", 1), String.class);
		assertThat(response.getBody(), equalTo(incorrectPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("entidad1", "13456", 2), String.class);
		assertThat(response.getBody(), equalTo(incorrectPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("sensor1", "23456", 3), String.class);
		assertThat(response.getBody(), equalTo(incorrectPassword));
	}

	@Test
	public void T7emptyId() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String emptyId = "{\"reason\": \"Users need an identificator \"}";
		response = template.postForEntity(userURI, new PeticionInfoREST("", "123", 1), String.class);
		assertThat(response.getBody(), equalTo(emptyId));

		response = template.postForEntity(userURI, new PeticionInfoREST("", "1223", 2), String.class);
		assertThat(response.getBody(), equalTo(emptyId));

		response = template.postForEntity(userURI, new PeticionInfoREST("", "iewgs", 3), String.class);
		assertThat(response.getBody(), equalTo(emptyId));

		response = template.postForEntity(userURI, new PeticionInfoREST(" ", "123", 1), String.class);
		assertThat(response.getBody(), equalTo(emptyId));
	}

	@Test
	public void T8invalidId() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String wrongIdStyle = "{\"reason\": \"Wrong id style\"}";

		response = template.postForEntity(userURI, new PeticionInfoREST("$%&", "123", 1), String.class);
		assertThat(response.getBody(), equalTo(wrongIdStyle));

		response = template.postForEntity(userURI, new PeticionInfoREST("_123", "123", 1), String.class);
		assertThat(response.getBody(), equalTo(wrongIdStyle));

		response = template.postForEntity(userURI, new PeticionInfoREST("-hhsy", "123", 1), String.class);
		assertThat(response.getBody(), equalTo(wrongIdStyle));

		response = template.postForEntity(userURI, new PeticionInfoREST("@jjA.", "123", 1), String.class);
		assertThat(response.getBody(), equalTo(wrongIdStyle));
	}

	@Test
	public void T9emptyPassword() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String emptyPassword = "{\"reason\": \"User password is required\"}";

		response = template.postForEntity(userURI, new PeticionInfoREST("12345678P", "", 1), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("12345678A", "", 1), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("entity1", "", 1), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("sensor1", "", 1), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));
	}

	@Test
	public void T10EmailRequiredChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String emptyEmail = "{\"reason\": \"User email is required\"}";

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("", "123456", "paco123@hotmail.com"),
				String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionChangeEmailREST(" ", "123456", "pajares123@hotmail.com"),
				String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("", "123456", "sensorT123@hotmail.com"),
				String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));
	}

	@Test
	public void T11newEmailRequiredChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String emptyEmail = "{\"reason\": \"User email is required\"}";

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco@hotmail.com", "123456", ""),
				String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("pajares@hotmail.com", "123456", " "),
				String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("sensorT@hotmail.com", "123456", " "),
				String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));
	}

	@Test
	public void T12invalidEmailChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String wrongEmailStyle = "{\"reason\": \"Wrong email style\"}";

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("asdasd", "123456", "paco123@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("@_zaasd", "123456", "pajares123@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("paco@@@hotmail", "123456", "sensorT123@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));
	}

	@Test
	public void T13newInvalidEmailChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String wrongEmailStyle = "{\"reason\": \"Wrong email style\"}";

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("paco@hotmail.com", "123456", "persona1-//~#"), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("pajares@hotmail.com", "123456", "persona1@%"), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("sensorT@hotmail.com", "123456", "12345asd"), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));
	}

	@Test
	public void T14EmailChangeUserNotFound() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String userNotFound = "{\"reason\": \"User not found\"}";

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("paco11@hotmail.com", "123456", "paco123@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("pajares33@hotmail.com", "123456", "pajarasdes@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("sensorTx@hotmail.com", "123456", "sensorT123@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));
	}

	@Test
	public void T15sameEmailErrorChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String sameEmail = "{\"reason\": \"Same email\"}";

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("paco@hotmail.com", "123456", "paco@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(sameEmail));

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("pajares@hotmail.com", "123456", "pajares@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(sameEmail));

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("sensorT@hotmail.com", "123456", "sensorT@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(sameEmail));
	}

	@Test
	public void T16IdRequiredPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String emptyEmail = "{\"reason\": \"User email is required\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("", "123456", "123"), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("", "123456", "123"), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("", "123456", "123"), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));
	}

	@Test
	public void T17inValidRequiredPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String wrongEmailStyle = "{\"reason\": \"Wrong email style\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("·$%", "123456", "123"),
				String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("asdd-eer", "123456", "123"),
				String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("shdgr@@hotmail", "123456", "123"),
				String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));
	}

	@Test
	public void T18passwordRequiredPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String passwordRequired = "{\"reason\": \"User password is required\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("paco@hotmail.com", "", "123"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("pajares@hotmail.com", "", "dkdddd"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("sensorT@hotmail.com", "", "dkejd"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));
	}

	@Test
	public void T19newPasswordRequiredPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String passwordRequired = "{\"reason\": \"User password is required\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("paco@hotmail.com", "123456", ""),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("pajares@hotmail.com", "123456", ""),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("sensorT@hotmail.com", "123456", ""),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));
	}

	@Test
	public void T20samePasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String passwordRequired = "{\"reason\": \"Password Incorrect\"}";

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("paco@hotmail.com", "123456", "123456"), String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("pajares@hotmail.com", "123456", "123456"), String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("sensorT@hotmail.com", "123456", "123456"), String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));
	}

	//
	@Test
	public void T21notFoundAgentPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String userNotFound = "{\"reason\": \"User not found\"}";

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("pac234o@hotmail.com", "djfhr", "djfhrtt"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("pajar234es@hotmail.com", "djvhrhc", "tt"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("sensor123T@hotmail.com", "dkejd", "tt"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));
	}

	@Test
	public void T22notSamePasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String passwordIncorrect = "{\"reason\": \"Password Incorrect\"}";

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("paco@hotmail.com", "123456", "123456"), String.class);
		assertThat(response.getBody(), equalTo(passwordIncorrect));

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("pajares@hotmail.com", "123456", "123456"), String.class);
		assertThat(response.getBody(), equalTo(passwordIncorrect));

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("sensorT@hotmail.com", "123456", "123456"), String.class);
		assertThat(response.getBody(), equalTo(passwordIncorrect));
	}

	@Test
	public void T23testHtmlController() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/";

		response = template.getForEntity(userURI, String.class);
		assertThat(response.getBody().replace(" ", "").replace("\n", "").replace("\t", ""), equalTo(new String(
				"<!DOCTYPEHTML><html><head><metacharset=\"UTF-8\"/><title>Login</title></head><body><div><formmethod="
						+ "\"POST\"action=\"login\"><h2>Usuario:</h2><inputid=\"ident\"name=\"ident\"type=\"text\"/><h2>Contraseña:</h2><inputid=\"password\"name=\"password\"type=\"password\"/><br/><br/><selectid=\"kind\"name=\"kind\"><optionvalue=\"1\">Person</option><optionvalue=\"2\">Entity</option><optionvalue=\"3\">Sensor</option></select><buttontype=\"submit\"id=\"login\">Entrar</button></form></div></body></html>")
								.replace("", "")));
	}

	@Test
	public void T24EmailChangeCorrect() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";

		String correctChange = "{\"agent\":\"paco33@hotmail.com\",\"message\":\"email actualizado correctamente\"}";
		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("paco@hotmail.com", "123456", "paco33@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));

		correctChange = "{\"agent\":\"pajares17@hotmail.com\",\"message\":\"email actualizado correctamente\"}";
		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("pajares@hotmail.com", "123456", "pajares17@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));

		correctChange = "{\"agent\":\"sensorTTIS@hotmail.com\",\"message\":\"email actualizado correctamente\"}";
		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("sensorT@hotmail.com", "123456", "sensorTTIS@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));
	}

	@Test
	public void T25correctPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String correctPassword = "{\"agent\":\"paco33@hotmail.com\",\"message\":\"contraseña actualizada correctamente\"}";

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("paco33@hotmail.com", "123456", "djfhr"), String.class);
		assertThat(response.getBody(), equalTo(correctPassword));
	}

	@Test
	public void T26correctPasswordChangeXML() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String correctChange = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ChangeInfoResponse><agent>paco33@hotmail.com</agent><message>contraseÃ±a actualizada correctamente</message></ChangeInfoResponse>";

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new AcceptInterceptor());

		template.setInterceptors(interceptors);

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("paco33@hotmail.com", "djfhr", "123456"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));
	}

	@Test
	public void T27idChangeCorrectXML() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String correctChange = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ChangeInfoResponse><agent>paco@hotmail.com</agent><message>email actualizado correctamente</message></ChangeInfoResponse>";

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new AcceptInterceptor());

		template.setInterceptors(interceptors);

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("paco33@hotmail.com", "123456", "paco@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));
	}

	@Test
	public void T28invalidKind() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String emptyId = "{\"reason\": \"Wrong kind style\"}";

		response = template.postForEntity(userURI, new PeticionInfoREST("12354678P", "123456", -6), String.class);
		assertThat(response.getBody(), equalTo(emptyId));

		response = template.postForEntity(userURI, new PeticionInfoREST("sensor1", "123456", 4), String.class);
		assertThat(response.getBody(), equalTo(emptyId));

		response = template.postForEntity(userURI, new PeticionInfoREST("sensor2", "123456", 33), String.class);
	}

	@Test
	public void T29EmptyKind() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String emptyId = "{\"reason\": \"User kind is required\"}";
		response = template.postForEntity(userURI, new PeticionInfoREST("12354678P", "123456", null), String.class);
		assertThat(response.getBody(), equalTo(emptyId));

		response = template.postForEntity(userURI, new PeticionInfoREST("entidad1", "123456", null), String.class);
		assertThat(response.getBody(), equalTo(emptyId));

		response = template.postForEntity(userURI, new PeticionInfoREST("sensor1", "123456", null), String.class);
		assertThat(response.getBody(), equalTo(emptyId));

		response = template.postForEntity(userURI, new PeticionInfoREST("sensor2", "123456", null), String.class);
	}

	// Cabecera HTTP para pedir respuesta en XML
	public class AcceptInterceptor implements ClientHttpRequestInterceptor {
		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
				throws IOException {
			HttpHeaders headers = request.getHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
			return execution.execute(request, body);
		}
	}
}
