package es.atrujillo.mjml.service.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.net.URI;
import java.util.*;

class PropertiesMjmlAuthConfTest {

    private final static String DUMMY_URI_STRING = "http://localhost/mjml";
    private MjmlAuthConf mjmlAuthConf;
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

        mjmlAuthConf = new PropertiesMjmlAuthConf(properties, appIDPropKey, secretKeyPropKey, mockURI);

    }

    @Test
    void testAuthInstance() {
        Assertions.assertNotNull(mjmlAuthConf);
        Assertions.assertNotNull(mjmlAuthConf.getMjmlApplicationId());
        Assertions.assertNotNull(mjmlAuthConf.getMjmlApplicationSecretKey());
        Assertions.assertNotNull(mjmlAuthConf.getMjmlApiEndpoint());
        Assertions.assertEquals(appIdVal, mjmlAuthConf.getMjmlApplicationId());
        Assertions.assertEquals(secretKeyVal, mjmlAuthConf.getMjmlApplicationSecretKey());
        Assertions.assertEquals(DUMMY_URI_STRING, mjmlAuthConf.getMjmlApiEndpoint().toString());
    }

    /*@Test
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

    }*/

}

