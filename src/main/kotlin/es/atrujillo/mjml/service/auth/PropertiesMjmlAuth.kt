package es.atrujillo.mjml.service.auth

import java.net.URI
import java.util.*

/**
 * @author Arnaldo Trujillo
 */
class PropertiesMjmlAuth private constructor(private val properties: Properties, private val mjmlAppIdKeyName: String,
                                             private val mjmlSecretKeyName: String) : MjmlAuth {

    private var mjmlApiEndpoint: URI = URI.create("https://api.mjml.io/v1")

    constructor(properties: Properties, mjmlAppIdKeyName: String, mjmlSecretKeyName: String, mjmlApiEndpoint: URI)
            : this(properties, mjmlAppIdKeyName, mjmlSecretKeyName) {

        this.mjmlApiEndpoint = mjmlApiEndpoint
    }

    override fun getMjmlApplicationId(): String = validateThatPropertyExistsAndGetValue(properties, mjmlAppIdKeyName)

    override fun getMjmlApplicationSecretKey(): String = validateThatPropertyExistsAndGetValue(properties, mjmlSecretKeyName)

    override fun getMjmlApiEndpoint(): URI = mjmlApiEndpoint

    private fun  validateThatPropertyExistsAndGetValue(properties: Properties, key: String) :String{
        if (properties.stringPropertyNames().contains(key))
            return properties.getProperty(key)

        throw IllegalArgumentException("Properties file don't contains $key key")
    }

}
