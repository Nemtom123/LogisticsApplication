package hu.webuni.logistics.dobiasz.web;

import hu.webuni.logistics.dobiasz.dto.LoginDto;
import hu.webuni.logistics.dobiasz.model.Address;
import hu.webuni.logistics.dobiasz.service.AddressServices;
import hu.webuni.logistics.dobiasz.service.InitDBService;
import hu.webuni.logistics.dobiasz.service.TransportPlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
		body.setUsername("user1");
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

	private String loginWithJwtOk(String username, String password) {
		LoginDto loginDto = new LoginDto(username, password);
		return webTestClient
				.post()
				.bodyValue(loginDto)
				.exchange()
				.expectStatus()
				.isOk()
		.expectBody(String.class).returnResult().getResponseBody();
		
		}

		private void loginWithJwtNotOk(String username, String password) {
		LoginDto loginDto = new LoginDto(username, password);
			webTestClient
				.post()
				.uri(LOGIN_URI)
				.bodyValue(loginDto)
				.exchange()
				.expectStatus()
				.isForbidden();

		}

	
}