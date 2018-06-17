package es.atrujillo.mjml.rest

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.util.*

/**
 *
 * @param <R> Request type
 * @author Arnaldo Trujillo
</R> */
internal abstract class HttpRestClient<R> protected constructor(protected var apiEndpoint: String) : RestClient<R>, Jacksonable {

    lateinit var restTemplate: RestTemplate

    private fun <T> httpRequest(request: R?, path: String, httpMethod: HttpMethod, type: ParameterizedTypeReference<T>,
                                params: MultiValueMap<String, String>? = null, headers: HttpHeaders? = null): ResponseEntity<T> {

        LOG.debug(String.format("%s HttpRequest to %s", httpMethod.toString(), apiEndpoint))

        var url = URI.create(apiEndpoint + path)
        if (Objects.nonNull(params)) {
            url = UriComponentsBuilder.fromUri(url).queryParams(params).build().toUri()
        }

        //request puede ser null en el caso de que la petición no tenga body
        val entityRequest = HttpEntity<R>(request, headers)

        return restTemplate.exchange(url, httpMethod, entityRequest, type)
    }

    override fun <T> get(path: String, type: ParameterizedTypeReference<T>, params: MultiValueMap<String, String>?,
                         headers: HttpHeaders?): ResponseEntity<T> = httpRequest(null, path, HttpMethod.GET, type, params, headers)


    override fun <T> post(request: R, path: String, type: ParameterizedTypeReference<T>,
                          params: MultiValueMap<String, String>?, headers: HttpHeaders?): ResponseEntity<T> = httpRequest(request, path, HttpMethod.POST, type, params, headers)

    override fun <T> patch(request: R, path: String, type: ParameterizedTypeReference<T>,
                           params: MultiValueMap<String, String>?, headers: HttpHeaders?): ResponseEntity<T> = httpRequest(request, path, HttpMethod.PATCH, type, params, headers)

    override fun getObjectMapper(): ObjectMapper {
        return restTemplate.messageConverters.stream()
                .filter { converter -> converter is MappingJackson2HttpMessageConverter }
                .findFirst()
                .map { jacksonConverter ->
                    (jacksonConverter as? MappingJackson2HttpMessageConverter)?.objectMapper ?: ObjectMapper()
                }
                .orElse(ObjectMapper())
    }


    companion object {
        private val LOG = LoggerFactory.getLogger(this::class.java)
    }
}
