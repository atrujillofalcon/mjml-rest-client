package es.atrujillo.mjml.integration.service

import es.atrujillo.mjml.config.template.TemplateFactory
import es.atrujillo.mjml.exception.MjmlApiErrorException
import es.atrujillo.mjml.service.auth.MjmlAuthFactory
import es.atrujillo.mjml.service.impl.MjmlRestService
import es.atrujillo.mjml.util.TestUtils.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

/**
 * @author Arnaldo Trujillo
 */
internal class MjmlRestServiceKtTest {

    private lateinit var template: String

    @BeforeEach
    private fun setUpTests() {
        template = TemplateFactory.builder()
                .withStringTemplate()
                .template(HELLO_WORLD_MJML)
                .buildTemplate()
    }


    /**
     * Test that valid mjml template is converted to html using MjmlService
     */
    @Test
    @DisplayName("Integration API test")
    fun testThatMjmlApiRespondCorrectly() {

        assertNotNull(MJML_APP_ID, "You have to configure environment variable MJML_APP_ID")
        assertNotNull(MJML_SECRET_KEY, "You have to configure environment variable MJML_SECRET_KEY")

        val authConf = MjmlAuthFactory.builder()
                .withEnvironmentCredentials()
                .mjmlKeyNames(MJML_APP_ID, MJML_SECRET_KEY)
                .build()

        val mjmlService = MjmlRestService(authConf)
        val response = mjmlService.transpileMjmlToHtml(template)

        assertNotNull(response)
        assertFalse(response.isEmpty())
    }

    /**
     * Test that valid mjml template is converted to html using MjmlService
     */
    @Test
    @DisplayName("Test Error Handling with Invalid Credentials")
    fun testThatApiReturnErrorWithoutValidCredentials() {

        val invalidAppId = UUID.randomUUID().toString()
        val invalidSecretKey = UUID.randomUUID().toString()

        val authConf = MjmlAuthFactory.builder()
                .withMemoryCredentials()
                .mjmlCredentials(invalidAppId, invalidSecretKey)
                .build()

        val mjmlService = MjmlRestService(authConf)

        Assertions.assertThrows(MjmlApiErrorException::class.java) { mjmlService.transpileMjmlToHtml(template) }

    }

}
