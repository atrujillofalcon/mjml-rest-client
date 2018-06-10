package es.atrujillo.mjml.service.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.util.UUID;

class MemoryMjmlAuthTest {

    private final static String DUMMY_URI_STRING = "http://localhost/mjml";
    private MjmlAuth mjmlAuth;
    private String appID;
    private String secretKey;

    @Test
    void testAuthInstance() {
        appID = UUID.randomUUID().toString();
        secretKey = UUID.randomUUID().toString();
        mjmlAuth = new MemoryMjmlAuth(appID, secretKey);

        Assertions.assertNotNull(mjmlAuth);
        Assertions.assertNotNull(mjmlAuth.getMjmlApplicationId());
        Assertions.assertNotNull(mjmlAuth.getMjmlApplicationSecretKey());
        Assertions.assertNotNull(mjmlAuth.getMjmlApiEndpoint());
        Assertions.assertEquals(appID, mjmlAuth.getMjmlApplicationId());
        Assertions.assertEquals(secretKey, mjmlAuth.getMjmlApplicationSecretKey());

        URI mockURI = Mockito.mock(URI.class);
        Mockito.when(mockURI.toString()).thenReturn(DUMMY_URI_STRING);
        mjmlAuth = new MemoryMjmlAuth(appID, secretKey, mockURI);
        Assertions.assertEquals(DUMMY_URI_STRING, mjmlAuth.getMjmlApiEndpoint().toString());

    }

    @Test
    void testAuthInstanceWithNullValues() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mjmlAuth = new MemoryMjmlAuth(null, null);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mjmlAuth = new MemoryMjmlAuth(UUID.randomUUID().toString(), null);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mjmlAuth = new MemoryMjmlAuth(null, UUID.randomUUID().toString());
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mjmlAuth = new MemoryMjmlAuth(UUID.randomUUID().toString(), UUID.randomUUID().toString(), null);
        });

    }

}

