package es.atrujillo.mjml.rest

import es.atrujillo.mjml.config.http.basicauth.HttpComponentsClientHttpRequestFactoryBasicAuth
import es.atrujillo.mjml.exception.InvalidMjmlApiUrlException
import es.atrujillo.mjml.util.RegexConstants
import org.apache.http.HttpHost
import org.springframework.http.client.support.BasicAuthorizationInterceptor
import org.springframework.web.client.RestTemplate

import java.net.URI

/**
 * Rest client with BasicAuth integrated
 * Each request going to have configured the basic auth token
 * {@link https://developer.mozilla.org/en-US/docs/Web/HTTP/Authentication}
 *
 * @author Arnaldo Trujillo
 * @param <R>
 */
internal class BasicAuthRestClient<R>(apiEndpoint: String, private val applicationID: String, private val secretKey: String) : HttpRestClient<R>(apiEndpoint) {

    init {
        restTemplate = configureRestTemplateWithBasicAuth()
    }

    private fun configureRestTemplateWithBasicAuth(): RestTemplate {
        if (!RegexConstants.URL_REGEX.matches(apiEndpoint))
            throw InvalidMjmlApiUrlException()

        val uriApiEndpoint = URI.create(apiEndpoint)
        val httpHost = HttpHost(uriApiEndpoint.host, uriApiEndpoint.port, uriApiEndpoint.scheme)

        val restTemplate = RestTemplate(HttpComponentsClientHttpRequestFactoryBasicAuth(httpHost))
        restTemplate.interceptors.add(BasicAuthorizationInterceptor(applicationID, secretKey))

        return restTemplate
    }
}
