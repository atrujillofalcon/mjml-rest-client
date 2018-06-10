package es.atrujillo.mjml.service.auth

import java.net.URI

interface MjmlAuth {
    fun getMjmlApplicationId(): String
    fun getMjmlApplicationSecretKey(): String
    fun getMjmlApiEndpoint(): URI
}
