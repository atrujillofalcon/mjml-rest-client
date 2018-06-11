package es.atrujillo.mjml.service.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Test Build Memory Auth")
    void testAuthInstance() {
        appID = UUID.randomUUID().toString();
        secretKey = UUID.randomUUID().toString();
        mjmlAuth = MjmlAuthFactory.builder()
                .withMemoryCredentials()
                .mjmlCredentials(appID, secretKey)
                .build();

        Assertions.assertNotNull(mjmlAuth);
        Assertions.assertNotNull(mjmlAuth.getMjmlApplicationId());
        Assertions.assertNotNull(mjmlAuth.getMjmlApplicationSecretKey());
        Assertions.assertNotNull(mjmlAuth.getMjmlApiEndpoint());
        Assertions.assertEquals(appID, mjmlAuth.getMjmlApplicationId());
        Assertions.assertEquals(secretKey, mjmlAuth.getMjmlApplicationSecretKey());

        URI mockURI = Mockito.mock(URI.class);
        Mockito.when(mockURI.toString()).thenReturn(DUMMY_URI_STRING);
        mjmlAuth = MjmlAuthFactory.builder()
                .withMemoryCredentials()
                .mjmlCredentials(appID, secretKey)
                .changeEndpoint(mockURI)
                .build();

        Assertions.assertEquals(DUMMY_URI_STRING, mjmlAuth.getMjmlApiEndpoint().toString());

    }

    @Test
    @DisplayName("Test That Fail When Null values")
    void testAuthInstanceWithNullValues() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mjmlAuth = MjmlAuthFactory.builder()
                    .withMemoryCredentials()
                    .mjmlCredentials(null, null)
                    .build();
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mjmlAuth = MjmlAuthFactory.builder()
                    .withMemoryCredentials()
                    .mjmlCredentials(UUID.randomUUID().toString(), null)
                    .build();
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mjmlAuth = MjmlAuthFactory.builder()
                    .withMemoryCredentials()
                    .mjmlCredentials(null, UUID.randomUUID().toString())
                    .build();
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MjmlAuthFactory.builder()
                    .withMemoryCredentials()
                    .mjmlCredentials(UUID.randomUUID().toString(), UUID.randomUUID().toString())
                    .changeEndpoint(null)
                    .build();
        });

    }

}

