package es.atrujillo.mjml.rest

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap

/**
 * RestClient Interface to execute HTTP call
 *
 * @author Arnaldo Trujillo
 */
internal interface RestClient<R> {

    /**
     * GET HTTP method abstraction
     *
     * @param path URL path
     * @param type Return object type expected
     * @param params URL query params (Optional)
     * @param headers HTTP header (Optional)
     */
    operator fun <T> get(path: String, type: ParameterizedTypeReference<T>, params: MultiValueMap<String, String>? = null,
                         headers: HttpHeaders? = null): ResponseEntity<T>

    /**
     * POST HTTP method abstraction
     *
     * @param request HTTP body
     * @param path URL path
     * @param type Return object type expected
     * @param params URL query params (Optional)
     * @param headers HTTP header (Optional)
     */
    fun <T> post(request: R, path: String, type: ParameterizedTypeReference<T>, params: MultiValueMap<String, String>? = null,
                 headers: HttpHeaders? = null): ResponseEntity<T>

    /**
     * PATCH HTTP method abstraction
     *
     * @param request HTTP body
     * @param path URL path
     * @param type Return object type expected
     * @param params URL query params (Optional)
     * @param headers HTTP header (Optional)
     */
    fun <T> patch(request: R, path: String, type: ParameterizedTypeReference<T>, params: MultiValueMap<String, String>? = null,
                  headers: HttpHeaders? = null): ResponseEntity<T>

}
