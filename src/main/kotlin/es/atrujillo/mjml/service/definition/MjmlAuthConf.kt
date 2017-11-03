package es.atrujillo.mjml.service.definition

import java.net.URI

interface MjmlAuthConf {
    fun getMjmlApplicationId(): String
    fun getMjmlApplicationSecretKey(): String
    fun getMjmlApiEndpoint(): URI
}
