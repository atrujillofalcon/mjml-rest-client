package es.atrujillo.mjml.rest

import es.atrujillo.mjml.exception.InvalidMjmlApiUrl
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.client.support.BasicAuthorizationInterceptor

class BasicAuthRestClientTest {

    private lateinit var restClient: BasicAuthRestClient<String>

    @BeforeEach
    fun initialize() {
        restClient = BasicAuthRestClient("https://api.mjml.io/v1", "appID", "secretKey")
    }

    @Test
    @DisplayName("Test RestTemplate Initialization")
    fun testRestTemplateInitialization() {
        assertNotNull(restClient)
        assertNotNull(restClient.restTemplate)
        assertNotNull(restClient.restTemplate.requestFactory)
        assertNotNull(restClient.restTemplate.messageConverters)
        assertTrue(restClient.restTemplate.interceptors.stream().anyMatch { it is BasicAuthorizationInterceptor })
    }

    @Test
    @DisplayName("When Invalid URL throw InvalidMjmlApiUrl Exception")
    fun testInitializationWithInvalidUrl() {
        assertThrows<InvalidMjmlApiUrl> {
            restClient = BasicAuthRestClient("invalid_url", "appID", "secretKey")
        }
    }

}