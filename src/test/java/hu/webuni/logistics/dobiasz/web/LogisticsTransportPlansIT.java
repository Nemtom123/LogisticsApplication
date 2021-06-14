package hu.webuni.logistics.dobiasz.web;

import hu.webuni.logistics.dobiasz.dto.LoginDto;
import hu.webuni.logistics.dobiasz.model.Address;
import hu.webuni.logistics.dobiasz.service.AddressServices;
import hu.webuni.logistics.dobiasz.service.InitDBService;
import hu.webuni.logistics.dobiasz.service.TransportPlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

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


	@Test
	void testThatCreatedAddressIsListed() throws Exception {
		List<Address> addressBefore = getAllAddress();

		Address newAddress = new Address(1L,"HU", "Győr", "Iván", "16", "2323", "12", "1");
		saveAddress(newAddress);

		List<Address> addressAfter = getAllAddress();

		assertThat(addressAfter.subList(0, addressBefore.size()))
				.usingRecursiveFieldByFieldElementComparator()
				.containsExactlyElementsOf(addressBefore);

		assertThat(addressAfter.get(addressAfter.size()-1))
				.usingRecursiveComparison()
				.isEqualTo(newAddress);
	}

	// nem volt feladat	
//	@Test
//	void testThatNewValidAddressCanBeSaved() throws Exception {
//		Address newAddress = new Address(1L,"HU", "Győr", "Iván", "16", "2323", "12", "1");
//		saveAddress(newAddress).expectStatus().isOk();
//		List<Address> addressBefore = getAllAddress();
//		List<Address> addressesAfter = getAllAddress();
//
//		assertThat(addressesAfter.size()).isEqualTo(addressBefore.size() + 1);
//
//		assertThat(addressesAfter.get(addressesAfter.size()-1))
//			.usingRecursiveComparison()
//			.ignoringFields("id")
//			.isEqualTo(newAddress);
//	}

//	// nem volt feladat
//	@Test
//	void testThatNewInvalidAddressCannotBeSaved() throws Exception {
//		List<Address> addressBefore = getAllAddress();
//		Address newAddress = newInvalidAddress();
//		saveAddress(newAddress).expectStatus().isBadRequest();
//		List<Address> addressAfter = getAllAddress();
//
//		assertThat(addressAfter).hasSameSizeAs(addressBefore);
//	}
//
//	private Address newInvalidAddress() {
//		return new Address(1L,"HU", "Győr", "Iván", "16", "2323", "12", "1");
//	}
//
//	// nem volt feladat
//	@Test
//	void testThatAddressCanBeUpdatedWithValidFields() throws Exception {
//
//		Address newAddress = new Address(1L,"HU", "Győr", "Iván", "16", "2323", "12", "1");
//		Address savedAddress = saveAddress(newAddress)
//				.expectStatus().isOk()
//				.expectBody(Address.class).returnResult().getResponseBody();
//
//		List<Address> addressBefore = getAllAddress();
//		savedAddress.setCity("modified");
//		modifyAddress(savedAddress).expectStatus().isOk();
//
//		List<Address> addressAfter = getAllAddress();
//
//		assertThat(addressAfter).hasSameSizeAs(addressBefore);
//		assertThat(addressAfter.get(addressAfter.size()-1))
//			.usingRecursiveComparison()
//			.isEqualTo(savedAddress);
//	}
//
//	// nem volt feladat
//	@Test
//	void testThatAddressCannotBeUpdatedWithInvalidFields() throws Exception {
//		Address newAddress = new Address(1L,"HU", "Bugyi", "Kanalas utca", "22", "3432", "12", "1");
//		Address savedAddress = saveAddress(newAddress)
//				.expectStatus().isOk()
//				.expectBody(Address.class).returnResult().getResponseBody();
//
//		List<Address> employeesBefore = getAllAddress();
//		Address invalidEmployee = newInvalidAddress();
//		invalidEmployee.setId(savedAddress.getId());
//		modifyAddress(invalidEmployee).expectStatus().isBadRequest();
//
//		List<Address> addressAfter = getAllAddress();
//
//		assertThat(addressAfter).hasSameSizeAs(employeesBefore);
//		assertThat(addressAfter.get(addressAfter.size()-1))
//			.usingRecursiveComparison()
//			.isEqualTo(savedAddress);
//	}
//
	
	private String loginAddressUser() {
		return loginWithJwtOk("addressUser", "passAddress");
		}

	private String loginTransportUser() {
		return loginWithJwtOk("transportUser", "passTransport");
		}

	private String loginAdminUser() {
		return loginWithJwtOk("admin", "passAdmin");
	}

		// nem volt feladat	
	private ResponseSpec saveAddress(Address address) {
		return webTestClient
				.post()
				.uri(BASE_URI).bodyValue(address)
				.headers(headers -> headers.setBearerAuth(jwtToken))
				.exchange();
	}
	
	// nem volt feladat	
	private List<Address> getAllAddress() {
		List<Address> responseList = webTestClient.get().uri(BASE_URI)
				.headers(headers -> headers.setBasicAuth("admin", "passAdmin"))
				.headers(headers -> headers.setBearerAuth(jwtToken))
				.exchange().expectStatus().isOk()
				.expectBodyList(Address.class).returnResult().getResponseBody();
		Collections.sort(responseList, (a1, a2) -> Long.compare(a1.getId(), a2.getId()));
		return responseList;
	}
	
	// nem volt feladat	
	private ResponseSpec modifyAddress(Address address) {
		String path = BASE_URI + "/" + address.getId();
		return webTestClient.put().uri(path)
				.headers(headers -> headers.setBasicAuth("transportUser", "passTransport"))
				.headers(headers -> headers.setBearerAuth(jwtToken))
				.bodyValue(address).exchange();
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