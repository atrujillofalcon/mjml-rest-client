package es.atrujillo.test.service;

import es.atrujillo.mjml.service.definition.MjmlAuthConf;
import es.atrujillo.mjml.service.definition.MjmlService;
import es.atrujillo.mjml.service.impl.MemoryMjmlAuthConf;
import es.atrujillo.mjml.service.impl.MjmlRestService;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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
    public void testThatMjmlApiRespondCorrectly() {
        assertNotNull("You have to configure environment variable MJML_APP_ID", MJML_APP_ID);
        assertNotNull("You have to configure environment variable MJML_SECRET_KEY", MJML_SECRET_KEY);

        MjmlAuthConf authConf = new MemoryMjmlAuthConf(MJML_APP_ID, MJML_SECRET_KEY);

        MjmlService mjmlService = new MjmlRestService(authConf);
        String response = mjmlService.transpileMjmlToHtml(HELLO_WORLD_MJML);

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

}
