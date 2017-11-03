package es.atrujillo.test.service;

import es.atrujillo.mjml.config.internal.MemoryMjmlAuthConf;
import es.atrujillo.mjml.service.definition.MjmlAuthConf;
import es.atrujillo.mjml.service.definition.MjmlService;
import es.atrujillo.mjml.service.impl.MjmlRestService;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * @author Arnaldo Trujillo
 */
public class MjmlRestServiceTest {

    private static final String HELLO_WORLD_MJML = "<mjml><mj-body><mj-container><mj-section><mj-column><mj-text>Hello World</mj-text></mj-column></mj-section></mj-container></mj-body></mjml>";

    @Test
    public void testThatMjmlApiRespondCorrectly(){
        MjmlAuthConf authConf = new MemoryMjmlAuthConf("f68687ee-f4cf-43d7-a535-8a7cebd9fd03",
                "ea9909f3-0e96-4618-baf9-367c0180b8e6");

        MjmlService mjmlService = new MjmlRestService(authConf);
        String response = mjmlService.transpileMjmlToHtml(HELLO_WORLD_MJML);

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

}
