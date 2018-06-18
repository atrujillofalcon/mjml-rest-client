package es.atrujillo.mjml.integration.service;

import es.atrujillo.mjml.config.template.TemplateFactory;
import es.atrujillo.mjml.service.auth.MjmlAuth;
import es.atrujillo.mjml.service.auth.MjmlAuthFactory;
import es.atrujillo.mjml.service.definition.MjmlService;
import es.atrujillo.mjml.service.impl.MjmlRestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static es.atrujillo.mjml.util.TestUtils.HELLO_WORLD_MJML;
import static es.atrujillo.mjml.util.TestUtils.MJML_APP_ID;
import static es.atrujillo.mjml.util.TestUtils.MJML_SECRET_KEY;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Arnaldo Trujillo
 */
class MjmlRestServiceJavaTest {


    /**
     * Test that valid mjml template is converted to html using MjmlService
     */
    @Test
    @DisplayName("Integration API test")
    void testThatMjmlApiRespondCorrectly() {

        assertNotNull(MJML_APP_ID, "You have to configure environment variable MJML_APP_ID");
        assertNotNull(MJML_SECRET_KEY, "You have to configure environment variable MJML_SECRET_KEY");

        MjmlAuth authConf = MjmlAuthFactory.builder()
                .withEnvironmentCredentials()
                .mjmlKeyNames(MJML_APP_ID, MJML_SECRET_KEY)
                .build();

        String template = TemplateFactory.builder()
                .withStringTemplate()
                .template(HELLO_WORLD_MJML)
                .buildTemplate();

        MjmlService mjmlService = new MjmlRestService(authConf);
        String response = mjmlService.transpileMjmlToHtml(template);

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }
}
