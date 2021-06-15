package hu.webuni.logistics.dobiasz.web;

import hu.webuni.logistics.dobiasz.dto.DelayDto;
import hu.webuni.logistics.dobiasz.dto.LoginDto;
import hu.webuni.logistics.dobiasz.service.AddressServices;
import hu.webuni.logistics.dobiasz.service.InitDBService;
import hu.webuni.logistics.dobiasz.service.TransportPlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LogisticsTransportPlansIT {

	private static final String BASE_URI = "/api/address";
	private static final String LOGIN_URI = "/api/login";


	@Autowired
	WebTestClient webTestClient;

	@Autowired
	AddressServices addressServices;

	@Autowired
	TransportPlanService transportPlanService;

	@Autowired
	InitDBService initDBService;


	private String jwtToken;

	@BeforeEach
	public void init() {
		LoginDto body = new LoginDto(jwtToken, jwtToken);
		body.setUsername("user");
		body.setPassword("pass");
		jwtToken = webTestClient.post()
				.uri("/api/login").bodyValue(body)
				.exchange().expectBody(String.class)
				.returnResult().getResponseBody();
	}



	@Test
	void testThatWeCannotLoginWithBadCredentials() throws Exception {
		loginWithJwtNotOk("baduser", "badpassword");
		loginWithJwtNotOk("admin", "badpassword");
		loginWithJwtNotOk("baduser", "passAdmin");
	}

	@Test
	void testThatWeCannotAddDelayWithInsufficientRights() throws Exception {
		String jwtToken = testThatWeLoginWithCredentials();
		addDelayToATransportPlan(10, 10, 100, jwtToken);
	}


	@Test
	public void Address() throws Exception {

	}


	@Test
	public void AddressDto() throws Exception {

	}

	@Test
	String testThatWeLoginWithCredentials() throws Exception {
		loginWithJwtOk("user", "pass");
		loginWithJwtOk("admin", "admin");
		loginWithJwtOk("user", "user");
		return null;
	}


	private void loginWithJwtNotOk(String username, String password) {
		LoginDto loginDto = new LoginDto(username, password);
		webTestClient
				.post()
				.bodyValue(loginDto)
				.exchange()
				.expectStatus()
				.isForbidden();

	}

	private void loginWithJwtOk(String username, String password) {
		LoginDto loginDto = new LoginDto(username, password);
		webTestClient
				.post()
				.bodyValue(loginDto)
				.exchange()
				.expectStatus()
				.isForbidden();

	}

	private void addDelayToATransportPlan(long transportPlanId, long milestoneId, int delayInMinutes,
											 String jwtToken) {
		DelayDto delayDto = new DelayDto(milestoneId, delayInMinutes);
		webTestClient.post().uri(BASE_URI + "/" + transportPlanId + "/delay")
				.headers(headers -> headers.setBearerAuth(jwtToken)).bodyValue(delayDto).exchange().expectStatus()
				.isEqualTo(HttpStatus.FORBIDDEN);
	}
}