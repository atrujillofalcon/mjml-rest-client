package es.atrujillo.mjml.model

import es.atrujillo.mjml.model.mjml.MjmlApiError
import es.atrujillo.mjml.model.mjml.MjmlError
import es.atrujillo.mjml.model.mjml.MjmlRequest
import es.atrujillo.mjml.model.mjml.MjmlResponse
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import pl.pojo.tester.api.assertion.Assertions
import pl.pojo.tester.api.assertion.Method
import kotlin.reflect.KClass

internal class ModelPojoTest {

    @Test
    @DisplayName("Test MjmlApiError POJO")
    internal fun testPojoMjmlApiError() = testPojoObject(MjmlApiError::class)

    @Test
    @DisplayName("Test MjmlError POJO")
    internal fun testPojoMjmlError() = testPojoObject(MjmlError::class)

    @Test
    @DisplayName("Test MjmlRequest POJO")
    internal fun testPojoMjmlRequest() = testPojoObject(MjmlRequest::class)

    @Test
    @DisplayName("Test MjmlResponse POJO")
    internal fun testPojoMjmlResponse() = testPojoObject(MjmlResponse::class)


    private fun testPojoObject(pojoClass: KClass<*>) {
        Assertions.assertPojoMethodsFor(pojoClass.java)
                .testing(Method.GETTER)
                .testing(Method.TO_STRING)
                .testing(Method.EQUALS)
                .testing(Method.HASH_CODE)
                .testing(Method.CONSTRUCTOR)
                .areWellImplemented()
    }
}