package es.atrujillo.mjml.service.auth

import java.net.URI

/**
 * Interface get information about mjml api credential for future calls
 *
 * @author Arnaldo Trujillo
 */
interface MjmlAuth {

    /**
     * Returns mjml api applicationId
     */
    fun getMjmlApplicationId(): String

    /**
     * Returns mjml api secret key token
     */
    fun getMjmlApplicationSecretKey(): String

    /**
     * Return API url
     */
    fun getMjmlApiEndpoint(): URI

}
