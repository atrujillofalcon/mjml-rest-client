package es.atrujillo.mjml.service.auth

import java.net.URI

/**
 * @author Arnaldo Trujillo
 */
class SystemEnvironmentMjmlAuth(val mjmlAppIdKeyName: String, val mjmlSecretKeyName: String) : MjmlAuth {

    private var mjmlApiEndpoint: URI = URI.create("https://api.mjml.io/v1")

    constructor(mjmlAppIdKeyName: String, mjmlSecretKeyName: String, mjmlApiEndpoint: URI) : this(mjmlAppIdKeyName, mjmlSecretKeyName) {
        this.mjmlApiEndpoint = mjmlApiEndpoint
    }

    override fun getMjmlApplicationId(): String = validateThatSystemVariableExistsAndGetValue(mjmlAppIdKeyName)

    override fun getMjmlApplicationSecretKey(): String = validateThatSystemVariableExistsAndGetValue(mjmlSecretKeyName)

    override fun getMjmlApiEndpoint(): URI = mjmlApiEndpoint

    private fun validateThatSystemVariableExistsAndGetValue(key: String): String {
        if (System.getenv().containsKey(key))
            return System.getenv(key)

        throw IllegalArgumentException("System environment don't contains a variable with $key key")
    }

}
