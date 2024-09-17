package com.example.order;

import com.example.order.stubs.InventoryClientStub;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup(){
		RestAssured.baseURI="http://localhost";
		RestAssured.port = port;
	}

	static {
		mySQLContainer.start();
	}

	@Test
	void shouldCreateOrder() {
		String requestBody = """
				{
				    "sku" : "xbox",
				    "price": 1000,
				    "quantity": 2
				}
				""";

		//Stubbed/Mocked call
		InventoryClientStub.stubInventoryCall("xbox",2);

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("api/order/create")
				.then()
				.statusCode(201)
				.body(Matchers.is("Order Created"));


//				.body("id", Matchers.notNullValue())
//				.body("sku", Matchers.equalTo("test Order 2"))
//				.body("quantity", Matchers.equalTo(2))
//				.body("price", Matchers.equalTo(1000));

//
//
//		assertThat(responseBodyString, Matchers.is("Order Created"));
	}

	@Test
	void shouldGetOrders()
	{

		RestAssured.given()
				.contentType("application/json")
				.when()
				.get("api/order/orders")
				.then()
				.statusCode(200)
				.body("$", Matchers.everyItem(Matchers.allOf(
						Matchers.hasKey("id"),
						Matchers.hasKey("sku"),
						Matchers.hasKey("quantity"),
						Matchers.hasKey("price")
				)));
	}
}
