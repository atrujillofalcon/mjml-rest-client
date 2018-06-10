package es.atrujillo.mjml.service.auth

import java.net.URI

/**
 * @author Arnaldo Trujillo
 */
class MemoryMjmlAuth(val mjmlAppId: String, val mjmlSecretKey: String) : MjmlAuth {

    private var mjmlApiEndpoint: URI = URI.create("https://api.mjml.io/v1")

    constructor(mjmlAppId: String, mjmlSecretKey: String, mjmlApiEndpoint: URI) : this(mjmlAppId, mjmlSecretKey) {
        this.mjmlApiEndpoint = mjmlApiEndpoint
    }

    override fun getMjmlApplicationId(): String = mjmlAppId
    override fun getMjmlApplicationSecretKey(): String = mjmlSecretKey
    override fun getMjmlApiEndpoint(): URI = mjmlApiEndpoint

}
