package es.atrujillo.mjml.rest

import es.atrujillo.mjml.config.basicauth.HttpComponentsClientHttpRequestFactoryBasicAuth
import org.apache.http.HttpHost
import org.springframework.http.client.support.BasicAuthorizationInterceptor
import org.springframework.web.client.RestTemplate

import java.net.URI

/**
 * Rest client with BasicAuth integrated
 * Each request will have the basic auth token
 *
 * @author Arnaldo Trujillo
 * @param <R>
</R> */
class BasicAuthRestClient<R>(apiEndpoint: String, private val applicationID: String, private val secretKey: String) : HttpRestClient<R>(apiEndpoint) {

    init {
        restTemplate = configureRestTemplateWithBasicAuth()
    }

    private fun configureRestTemplateWithBasicAuth(): RestTemplate {
        val uriApiEndpoint = URI.create(apiEndpoint)
        val httpHost = HttpHost(uriApiEndpoint.host, uriApiEndpoint.port, uriApiEndpoint.scheme)

        val restTemplate = RestTemplate(HttpComponentsClientHttpRequestFactoryBasicAuth(httpHost))
        restTemplate.interceptors.add(BasicAuthorizationInterceptor(applicationID, secretKey))

        return restTemplate
    }
}
