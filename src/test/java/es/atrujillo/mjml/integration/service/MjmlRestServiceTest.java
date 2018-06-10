package es.atrujillo.mjml.integration.service;

import es.atrujillo.mjml.config.template.TemplateFactory;
import es.atrujillo.mjml.config.template.TemplateType;
import es.atrujillo.mjml.service.definition.MjmlAuthConf;
import es.atrujillo.mjml.service.definition.MjmlService;
import es.atrujillo.mjml.service.impl.MemoryMjmlAuthConf;
import es.atrujillo.mjml.service.impl.MjmlRestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Arnaldo Trujillo
 */
public class MjmlRestServiceTest {

    private static final String HELLO_WORLD_MJML = "<mjml><mj-body><mj-container><mj-section><mj-column><mj-text>Hello World</mj-text></mj-column></mj-section></mj-container></mj-body></mjml>";

    private static final String MJML_APP_ID = System.getenv("MJML_APP_ID");
    private static final String MJML_SECRET_KEY = System.getenv("MJML_SECRET_KEY");

    /**
     * Test that valid mjml template is converted to html using MjmlService
     */
    @Test
    @DisplayName("Integration API test")
    public void testThatMjmlApiRespondCorrectly() {
        assertNotNull("You have to configure environment variable MJML_APP_ID", MJML_APP_ID);
        assertNotNull("You have to configure environment variable MJML_SECRET_KEY", MJML_SECRET_KEY);

        String template = TemplateFactory.builder()
                .type(TemplateType.STRING)
                .template(HELLO_WORLD_MJML)
                .buildTemplate();

        MjmlAuthConf authConf = new MemoryMjmlAuthConf(MJML_APP_ID, MJML_SECRET_KEY);

        MjmlService mjmlService = new MjmlRestService(authConf);
        String response = mjmlService.transpileMjmlToHtml(template);

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

}