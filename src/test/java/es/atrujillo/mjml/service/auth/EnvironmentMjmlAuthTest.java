package es.atrujillo.mjml.service.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * Test Environment authentication
 */
class EnvironmentMjmlAuthTest {


    /**
     * Test invalid arguments
     */
    @Test
    @DisplayName("Test Build Properties Auth")
    void testNotFoundEnvironmentVariables() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MjmlAuthFactory.builder()
                    .withEnvironmentCredentials()
                    .mjmlKeyNames(null, null)
                    .build();
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MjmlAuthFactory.builder()
                    .withEnvironmentCredentials()
                    .mjmlKeyNames(UUID.randomUUID().toString(), UUID.randomUUID().toString())
                    .build()
                    .getMjmlApplicationId();
        });
    }

}

