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

class ModelPojoTest {

    @Test
    @DisplayName("Test MjmlApiError POJO")
    fun testPojoMjmlApiError() = testPojoObject(MjmlApiError::class)

    @Test
    @DisplayName("Test MjmlError POJO")
    fun testPojoMjmlError() = testPojoObject(MjmlError::class)

    @Test
    @DisplayName("Test MjmlRequest POJO")
    fun testPojoMjmlRequest() = testPojoObject(MjmlRequest::class)

    @Test
    @DisplayName("Test MjmlResponse POJO")
    fun testPojoMjmlResponse() = testPojoObject(MjmlResponse::class)


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