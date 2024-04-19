package com.sena.inventory_manager

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryManagerApplicationTests(val client: TestRestTemplate) {

	@Test
	fun contextLoads() {

	}

}
