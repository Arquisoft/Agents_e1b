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
import asw.agents.webService.request.PeticionChangeIdREST;
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
						+ agent1.getEmail() + ", id=" + agent1.getIdent() + ", kind=" + agent1.getKind() + ", kindCode="
						+ agent1.getKindCode() + "]");
	}

	@Test
	public void T3domainModelHashCodeTest() {
		Agent agent1 = getAgent.getAgent("12345678P");
		Agent agent3 = getAgent.getAgent("12345678P");
		assertEquals(agent1.hashCode(), agent3.hashCode());
	}

	@Test
	public void T4participantExistAndCorrectPasssword() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";

		// PERSON
		response = template.postForEntity(userURI, new PeticionInfoREST("12345678P", "123456", "Person"), String.class);
		assertThat(response.getBody(), equalTo(
				"{\"name\":\"Paco\",\"location\":\"43.5479621,-5.9304147\",\"email\":\"paco@hotmail.com\",\"id\":\"12345678P\",\"kind\":\"Person\",\"kindCode\":\"1\"}"));

		// ENTITY
		response = template.postForEntity(userURI, new PeticionInfoREST("entidad1", "123456", "Entity"), String.class);
		assertThat(response.getBody(), equalTo(
				"{\"name\":\"Valgrande Pajares\",\"location\":\"43.5479621,-5.9304147\",\"email\":\"pajares@hotmail.com\",\"id\":\"32345678P\",\"kind\":\"Entity\",\"kindCode\":\"2\"}"));

		// SENSOR
		response = template.postForEntity(userURI, new PeticionInfoREST("sensor1", "123456", "Sensor"), String.class);
		assertThat(response.getBody(), equalTo(
				"{\"name\":\"SensorTemperatura\",\"location\":\"43.5479621,-5.9304147\",\"email\":\"sensorT@hotmail.com\",\"id\":\"52345678P\",\"kind\":\"Sensor\",\"kindCode\":\"3\"}"));
	}

	@Test
	public void T5participantDoNotExist() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String userNotFound = "{\"reason\": \"User not found\"}";

		response = template.postForEntity(userURI, new PeticionInfoREST("sensor33", "ajksdkje", "asdad"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));

		response = template.postForEntity(userURI, new PeticionInfoREST("persona69", "shcxhqw", "qwerty"),
				String.class);
		assertThat(response.getBody(), equalTo(userNotFound));
	}

	@Test
	public void T6incorrectPassword() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String incorrectPassword = "{\"reason\": \"Password do not match\"}";

		response = template.postForEntity(userURI, new PeticionInfoREST("12345678P", "12356", "Person"), String.class);
		assertThat(response.getBody(), equalTo(incorrectPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("12345678A", "12346", "Person"), String.class);
		assertThat(response.getBody(), equalTo(incorrectPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("entidad1", "13456", "Entity"), String.class);
		assertThat(response.getBody(), equalTo(incorrectPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("sensor1", "23456", "Sensor"), String.class);
		assertThat(response.getBody(), equalTo(incorrectPassword));
	}

	@Test
	public void T7emptyId() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String emptyId = "{\"reason\": \"User id is required\"}";
		response = template.postForEntity(userURI, new PeticionInfoREST("", "123", "Person"), String.class);
		assertThat(response.getBody(), equalTo(emptyId));

		response = template.postForEntity(userURI, new PeticionInfoREST("", "1223", "Entity"), String.class);
		assertThat(response.getBody(), equalTo(emptyId));

		response = template.postForEntity(userURI, new PeticionInfoREST("", "iewgs", "Sensor"), String.class);
		assertThat(response.getBody(), equalTo(emptyId));

		response = template.postForEntity(userURI, new PeticionInfoREST("   ", "123", "Person"), String.class);
		assertThat(response.getBody(), equalTo(emptyId));
	}

	@Test
	public void T8invalidId() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String wrongIdStyle = "{\"reason\": \"Wrong id style\"}";

		response = template.postForEntity(userURI, new PeticionInfoREST("$%&", "123", "Person"), String.class);
		assertThat(response.getBody(), equalTo(wrongIdStyle));

		response = template.postForEntity(userURI, new PeticionInfoREST("_123", "123", "Person"), String.class);
		assertThat(response.getBody(), equalTo(wrongIdStyle));

		response = template.postForEntity(userURI, new PeticionInfoREST("-hhsy", "123", "Person"), String.class);
		assertThat(response.getBody(), equalTo(wrongIdStyle));

		response = template.postForEntity(userURI, new PeticionInfoREST("@jjA.", "123", "Person"), String.class);
		assertThat(response.getBody(), equalTo(wrongIdStyle));
	}

	@Test
	public void T9emptyPassword() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String emptyPassword = "{\"reason\": \"User password is required\"}";

		response = template.postForEntity(userURI, new PeticionInfoREST("12345678P", "", "Person"), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("12345678A", "", "Person"), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("entity1", "", "Person"), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("sensor1", "", "Person"), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));
	}

	@Test
	public void T10IdRequiredChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeId";
		String emptyId = "{\"reason\": \"User id is required\"}";

		response = template.postForEntity(userURI, new PeticionChangeIdREST("", "123456", "xlr8"), String.class);
		assertThat(response.getBody(), equalTo(emptyId));

		response = template.postForEntity(userURI, new PeticionChangeIdREST("	", "123456", "asd"), String.class);
		assertThat(response.getBody(), equalTo(emptyId));

		response = template.postForEntity(userURI, new PeticionChangeIdREST("", "123456", "shfhs"), String.class);
		assertThat(response.getBody(), equalTo(emptyId));
	}

	@Test
	public void T12newIdRequiredChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeId";
		String emptyId = "{\"reason\": \"User id is required\"}";

		response = template.postForEntity(userURI, new PeticionChangeIdREST("12345678P", "123456", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyId));

		response = template.postForEntity(userURI, new PeticionChangeIdREST("entity1", "123456", "   "), String.class);
		assertThat(response.getBody(), equalTo(emptyId));

		response = template.postForEntity(userURI, new PeticionChangeIdREST("sensor1", "123456", "	"), String.class);
		assertThat(response.getBody(), equalTo(emptyId));
	}

	@Test
	public void T13invalidEmailChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeId";
		String wrongIdStyle = "{\"reason\": \"Wrong id style\"}";

		response = template.postForEntity(userURI, new PeticionChangeIdREST("$%&", "123456", "xlr8"), String.class);
		assertThat(response.getBody(), equalTo(wrongIdStyle));

		response = template.postForEntity(userURI, new PeticionChangeIdREST("@_zaasd", "123456", "xlr8"), String.class);
		assertThat(response.getBody(), equalTo(wrongIdStyle));

		response = template.postForEntity(userURI, new PeticionChangeIdREST("paco@hotmail", "123456", "xlr8"),
				String.class);
		assertThat(response.getBody(), equalTo(wrongIdStyle));
	}

	@Test
	public void T14newInvalidIdChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeId";
		String wrongIdStyle = "{\"reason\": \"Wrong id style\"}";

		response = template.postForEntity(userURI, new PeticionChangeIdREST("12345678P", "123456", "persona1-//~#"),
				String.class);
		assertThat(response.getBody(), equalTo(wrongIdStyle));

		response = template.postForEntity(userURI, new PeticionChangeIdREST("12345678P", "123456", "persona1@%"),
				String.class);
		assertThat(response.getBody(), equalTo(wrongIdStyle));

		response = template.postForEntity(userURI,
				new PeticionChangeIdREST("12345678P", "123456" + "" + "", "12345678R//"), String.class);
		assertThat(response.getBody(), equalTo(wrongIdStyle));
	}


	@Test
	public void T15emailChangeUserNotFound() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String userNotFound = "{\"reason\": \"User not found\"}";

		response = template.postForEntity(userURI,
				new PeticionChangeIdREST("pao@hotmail.com", "123456", "pac@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));

		response = template.postForEntity(userURI,
				new PeticionChangeIdREST("pee@gmail.com", "123456", "pepe@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));

		response = template.postForEntity(userURI,
				new PeticionChangeIdREST("pa@hotmail.com", "123456", "fhfyg@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));
	}

	@Test
	public void T16sameEmailErrorChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String sameEmail = "{\"reason\": \"Same email\"}";

		response = template.postForEntity(userURI, new PeticionChangeIdREST("paco@hotmail.com", "", "paco@hotmail.com"),
				String.class);
		assertThat(response.getBody(), equalTo(sameEmail));

		response = template.postForEntity(userURI, new PeticionChangeIdREST("pepe@gmail.com", "", "pepe@gmail.com"),
				String.class);
		assertThat(response.getBody(), equalTo(sameEmail));

		response = template.postForEntity(userURI, new PeticionChangeIdREST("carmen@yahoo.com", "", "carmen@yahoo.com"),
				String.class);
		assertThat(response.getBody(), equalTo(sameEmail));
	}

	@Test
	public void T17emailRequiredPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String emptyEmail = "{\"reason\": \"User email is required\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("", "", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("	", "chsh", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("", "dfhe", "dhdgx"), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));
	}

	@Test
	public void T18inValidRequiredPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String wrongEmailStyle = "{\"reason\": \"Wrong mail style\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("shdgr", "", ""), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("shdgr@", "", ""), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("shdgr@hotmail", "", ""),
				String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));
	}

	@Test
	public void T19passwordRequiredPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String passwordRequired = "{\"reason\": \"User password is required\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("paco@hotmail.com", "", ""),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("paco@hotmail.com", "", "dkdddd"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("paco@hotmail.com", "", "dkejd"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));
	}

	@Test
	public void T20newPasswordRequiredPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String passwordRequired = "{\"reason\": \"User password is required\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("paco@hotmail.com", "djfhr", ""),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("paco@hotmail.com", "djvhrhc", ""),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("paco@hotmail.com", "dkejd", ""),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));
	}

	@Test
	public void T21samePasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String passwordRequired = "{\"reason\": \"Password Incorrect\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("paco@hotmail.com", "djfhr", "djfhr"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("paco@hotmail.com", "djvhrhc", "djvhrhc"), String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("paco@hotmail.com", "dkejd", "dkejd"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));
	}

	@Test
	public void T22notFoundParticipantPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String userNotFound = "{\"reason\": \"User not found\"}";

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("pac@hotmail.com", "djfhr", "djfhrtt"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("martin@hotmail.com", "djvhrhc", "tt"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("juan@hotmail.com", "dkejd", "tt"),
				String.class);
		assertThat(response.getBody(), equalTo(userNotFound));
	}

	@Test
	public void T23notSamePasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String passwordIncorrect = "{\"reason\": \"Password Incorrect\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("paco@hotmail.com", "djfhr", "djfhr"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordIncorrect));

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("pepe@gmail.com", "djvhrhc", "djvhrhc"), String.class);
		assertThat(response.getBody(), equalTo(passwordIncorrect));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("carmen@yahoo.com", "dkejd", "dkejd"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordIncorrect));
	}

	@Test
	public void T24testHtmlController() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/";

		response = template.getForEntity(userURI, String.class);
		assertThat(response.getBody().replace(" ", "").replace("\n", "").replace("\t", ""),
				equalTo(new String("<!DOCTYPEHTML><html><head><metacharset=\"UTF-8\"/><title>Login</title></head><body>"
						+ "<h1>Login</h1><formmethod=\"POST\"action=\"login\"><table><tr><td><labelfor=\"email\">"
						+ "<strong>Usuario:</strong></label></td><td><inputtype=\"text\"id=\"email\"name=\"email\"/>"
						+ "</td></tr><tr><td><labelfor=\"password\"><strong>Contraseña:</strong></label></td><td>"
						+ "<inputtype=\"password\"id=\"password\"name=\"password\"/></td></tr><tr><td><buttontype="
						+ "\"submit\"id=\"login\">Entrar</button></td></tr></table></form></body></html>").replace(" ",
								"")));
	}

	@Test
	public void emailChangeCorrect() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";

		String correctChange = "{\"participant\":\"pac@hotmail.com\",\"message\":\"email actualizado correctamente\"}";
		response = template.postForEntity(userURI,
				new PeticionChangeIdREST("paco@hotmail.com", "123456", "pac@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));

		correctChange = "{\"participant\":\"pepe@hotmail.com\",\"message\":\"email actualizado correctamente\"}";
		response = template.postForEntity(userURI,
				new PeticionChangeIdREST("pepe@gmail.com", "123456", "pepe@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));

		correctChange = "{\"participant\":\"fhfyg@hotmail.com\",\"message\":\"email actualizado correctamente\"}";
		response = template.postForEntity(userURI,
				new PeticionChangeIdREST("carmen@yahoo.com", "123456", "fhfyg@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));
	}

	@Test
	public void correctPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String correctPassword = "{\"participant\":\"isabel@gmail.com\",\"message\":\"contraseña actualizada correctamente\"}";

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("isabel@gmail.com", "123456", "djfhr"), String.class);
		assertThat(response.getBody(), equalTo(correctPassword));
	}

	@Test
	public void correctPasswordChangeXML() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String correctChange = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<ChangeInfoResponse><message>contraseÃ±a actualizada correctamente</message>"
				+ "<participant>isabel@gmail.com</participant></ChangeInfoResponse>";

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new AcceptInterceptor());

		template.setInterceptors(interceptors);

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("isabel@gmail.com", "djfhr", "123456"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));
	}

	@Test
	public void emailChangeCorrectXML() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String correctChange = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<ChangeInfoResponse><message>email actualizado correctamente</message>"
				+ "<participant>carmen@yahoo.com</participant></ChangeInfoResponse>";

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new AcceptInterceptor());

		template.setInterceptors(interceptors);

		response = template.postForEntity(userURI,
				new PeticionChangeIdREST("fhfyg@hotmail.com", "123456", "carmen@yahoo.com"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));
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
