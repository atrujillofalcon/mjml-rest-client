package es.atrujillo.mjml.integration.service;

import es.atrujillo.mjml.config.template.TemplateFactory;
import es.atrujillo.mjml.service.auth.MjmlAuth;
import es.atrujillo.mjml.service.auth.MjmlAuthFactory;
import es.atrujillo.mjml.service.definition.MjmlService;
import es.atrujillo.mjml.service.impl.MjmlRestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Arnaldo Trujillo
 */
class MjmlRestServiceJavaTest {

    private static final String HELLO_WORLD_MJML = "<mjml><mj-body><mj-container><mj-section><mj-column><mj-text>Hello World</mj-text></mj-column></mj-section></mj-container></mj-body></mjml>";

    private static final String MJML_APP_ID = "MJML_APP_ID";
    private static final String MJML_SECRET_KEY = "MJML_SECRET_KEY";

    private String template;

    @BeforeEach
    private void setUpTests() {
        template = TemplateFactory.builder()
                .withStringTemplate()
                .template(HELLO_WORLD_MJML)
                .buildTemplate();
    }


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

        MjmlService mjmlService = new MjmlRestService(authConf);
        String response = mjmlService.transpileMjmlToHtml(template);

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }
}
