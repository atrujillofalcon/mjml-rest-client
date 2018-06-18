package es.atrujillo.mjml.rest

import es.atrujillo.mjml.exception.InvalidMjmlApiUrlException
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.client.support.BasicAuthorizationInterceptor

internal class BasicAuthRestClientTest {

    private lateinit var restClient: BasicAuthRestClient<String>

    @BeforeEach
    internal fun initialize() {
        restClient = BasicAuthRestClient("https://api.mjml.io/v1", "appID", "secretKey")
    }

    @Test
    @DisplayName("Test RestTemplate Initialization")
    internal fun testRestTemplateInitialization() {
        assertNotNull(restClient)
        assertNotNull(restClient.restTemplate)
        assertNotNull(restClient.restTemplate.requestFactory)
        assertNotNull(restClient.restTemplate.messageConverters)
        assertTrue(restClient.restTemplate.interceptors.stream().anyMatch { it is BasicAuthorizationInterceptor })
    }

    @Test
    @DisplayName("When Invalid URL throw InvalidMjmlApiUrl Exception")
    internal fun testInitializationWithInvalidUrl() {
        assertThrows<InvalidMjmlApiUrlException> {
            restClient = BasicAuthRestClient("invalid_url", "appID", "secretKey")
        }
    }

}