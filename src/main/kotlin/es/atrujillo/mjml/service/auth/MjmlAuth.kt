package es.atrujillo.mjml.service.auth

import java.net.URI

/**
 * Interface get information about mjml api credential for future calls
 *
 * @author Arnaldo Trujillo
 */
interface MjmlAuth {

    fun getMjmlApplicationId(): String

    fun getMjmlApplicationSecretKey(): String

    fun getMjmlApiEndpoint(): URI

}
