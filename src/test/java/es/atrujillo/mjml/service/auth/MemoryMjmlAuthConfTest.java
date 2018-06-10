package es.atrujillo.mjml.service.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.util.UUID;

class MemoryMjmlAuthConfTest {

    private final static String DUMMY_URI_STRING = "http://localhost/mjml";
    private MjmlAuthConf mjmlAuthConf;
    private String appID;
    private String secretKey;

    @Test
    void testAuthInstance() {
        appID = UUID.randomUUID().toString();
        secretKey = UUID.randomUUID().toString();
        mjmlAuthConf = new MemoryMjmlAuthConf(appID, secretKey);

        Assertions.assertNotNull(mjmlAuthConf);
        Assertions.assertNotNull(mjmlAuthConf.getMjmlApplicationId());
        Assertions.assertNotNull(mjmlAuthConf.getMjmlApplicationSecretKey());
        Assertions.assertNotNull(mjmlAuthConf.getMjmlApiEndpoint());
        Assertions.assertEquals(appID, mjmlAuthConf.getMjmlApplicationId());
        Assertions.assertEquals(secretKey, mjmlAuthConf.getMjmlApplicationSecretKey());

        URI mockURI = Mockito.mock(URI.class);
        Mockito.when(mockURI.toString()).thenReturn(DUMMY_URI_STRING);
        mjmlAuthConf = new MemoryMjmlAuthConf(appID, secretKey, mockURI);
        Assertions.assertEquals(DUMMY_URI_STRING,mjmlAuthConf.getMjmlApiEndpoint().toString());

    }

    @Test
    void testAuthInstanceWithNullValues() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mjmlAuthConf = new MemoryMjmlAuthConf(null, null);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mjmlAuthConf = new MemoryMjmlAuthConf(UUID.randomUUID().toString(), null);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mjmlAuthConf = new MemoryMjmlAuthConf(null, UUID.randomUUID().toString());
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mjmlAuthConf = new MemoryMjmlAuthConf(UUID.randomUUID().toString(), UUID.randomUUID().toString(), null);
        });

    }

}

