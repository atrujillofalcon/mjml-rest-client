package es.atrujillo.mjml.rest

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap

interface RestClient<R> {

    operator fun <T> get(path: String, type: ParameterizedTypeReference<T>, params: MultiValueMap<String, String>?, headers: HttpHeaders?): ResponseEntity<T>

    fun <T> post(request: R, path: String, type: ParameterizedTypeReference<T>, params: MultiValueMap<String, String>?, headers: HttpHeaders?): ResponseEntity<T>

    fun <T> patch(request: R, path: String, type: ParameterizedTypeReference<T>, params: MultiValueMap<String, String>?, headers: HttpHeaders?): ResponseEntity<T>
}
