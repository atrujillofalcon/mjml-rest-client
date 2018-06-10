package es.atrujillo.mjml.service.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class SystemEnvMjmlAuthTest {

    private final static String DUMMY_URI_STRING = "http://localhost/mjml";
    private MjmlAuth mjmlAuth;
    private String appIDEnvKey;
    private String secretKeyEnv;
    private String appIdVal;
    private String secretKeyVal;

    @BeforeEach
    void initialize() {
        appIDEnvKey = UUID.randomUUID().toString();
        secretKeyEnv = UUID.randomUUID().toString();
        appIdVal = UUID.randomUUID().toString();
        secretKeyVal = UUID.randomUUID().toString();

        URI mockURI = Mockito.mock(URI.class);
        Mockito.when(mockURI.toString()).thenReturn(DUMMY_URI_STRING);

        mjmlAuth = new SystemEnvironmentMjmlAuth(appIDEnvKey, secretKeyEnv, mockURI);

        SystemEnvironmentMjmlAuth.Companion companion = Mockito.mock(SystemEnvironmentMjmlAuth.Companion.getClass());
        Map<String, String> envMap = new HashMap<>();
        envMap.put(appIDEnvKey, appIdVal);
        envMap.put(secretKeyEnv, secretKeyVal);

        Mockito.when(companion.getEnvironmentVariables()).thenReturn(envMap);
    }

    @Test
    void testAuthInstance() {
        Assertions.assertNotNull(mjmlAuth);
        Assertions.assertNotNull(mjmlAuth.getMjmlApplicationId());
        Assertions.assertNotNull(mjmlAuth.getMjmlApplicationSecretKey());
        Assertions.assertNotNull(mjmlAuth.getMjmlApiEndpoint());
        Assertions.assertEquals(appIdVal, mjmlAuth.getMjmlApplicationId());
        Assertions.assertEquals(secretKeyVal, mjmlAuth.getMjmlApplicationSecretKey());
        Assertions.assertEquals(DUMMY_URI_STRING, mjmlAuth.getMjmlApiEndpoint().toString());
    }

}

