package es.atrujillo.mjml.service.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.net.URI;
import java.util.*;

class PropertiesMjmlAuthTest {

    private final static String DUMMY_URI_STRING = "http://localhost/mjml";
    private MjmlAuth mjmlAuth;
    private String appIDPropKey;
    private String secretKeyPropKey;
    private String appIdVal;
    private String secretKeyVal;
    private Properties properties;

    @BeforeEach
    void initialize() {
        appIDPropKey = UUID.randomUUID().toString();
        secretKeyPropKey = UUID.randomUUID().toString();
        appIdVal = UUID.randomUUID().toString();
        secretKeyVal = UUID.randomUUID().toString();

        properties = Mockito.mock(Properties.class);
        Set<String> propKeys = new HashSet<>(Arrays.asList(appIDPropKey, secretKeyPropKey));

        Mockito.when(properties.stringPropertyNames()).thenReturn(propKeys);
        Mockito.when(properties.getProperty(ArgumentMatchers.eq(appIDPropKey))).thenReturn(appIdVal);
        Mockito.when(properties.getProperty(ArgumentMatchers.eq(secretKeyPropKey))).thenReturn(secretKeyVal);

        URI mockURI = Mockito.mock(URI.class);
        Mockito.when(mockURI.toString()).thenReturn(DUMMY_URI_STRING);

        mjmlAuth = MjmlAuthFactory.builder()
                .withPropertiesCredential()
                .properties(properties)
                .mjmlKeyNames(appIDPropKey, secretKeyPropKey)
                .changeEndpoint(mockURI)
                .build();

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

    @Test
    void testAuthInstanceWithNullValues() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MjmlAuthFactory.builder()
                    .withPropertiesCredential()
                    .properties(properties)
                    .mjmlKeyNames(null, null)
                    .build();
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MjmlAuthFactory.builder()
                    .withPropertiesCredential()
                    .properties(properties)
                    .mjmlKeyNames(UUID.randomUUID().toString(), null)
                    .build();
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MjmlAuthFactory.builder()
                    .withPropertiesCredential()
                    .properties(properties)
                    .mjmlKeyNames(null, UUID.randomUUID().toString())
                    .build();
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MjmlAuthFactory.builder()
                    .withPropertiesCredential()
                    .properties(properties)
                    .mjmlKeyNames(UUID.randomUUID().toString(), UUID.randomUUID().toString())
                    .changeEndpoint(null)
                    .build();
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MjmlAuth testMjmlAuth = MjmlAuthFactory.builder()
                    .withPropertiesCredential()
                    .properties(properties)
                    .mjmlKeyNames("invalid", secretKeyPropKey)
                    .build();
            testMjmlAuth.getMjmlApplicationId();
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MjmlAuth testMjmlAuth = MjmlAuthFactory.builder()
                    .withPropertiesCredential()
                    .properties(properties)
                    .mjmlKeyNames(appIDPropKey,"invalid")
                    .build();
            testMjmlAuth.getMjmlApplicationSecretKey();
        });

    }

}

