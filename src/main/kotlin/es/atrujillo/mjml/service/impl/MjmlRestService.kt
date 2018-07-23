package es.atrujillo.mjml.service.impl

import com.fasterxml.jackson.module.kotlin.readValue
import es.atrujillo.mjml.exception.MjmlApiErrorException
import es.atrujillo.mjml.model.mjml.MjmlApiError
import es.atrujillo.mjml.model.mjml.MjmlRequest
import es.atrujillo.mjml.model.mjml.MjmlResponse
import es.atrujillo.mjml.rest.BasicAuthRestClient
import es.atrujillo.mjml.service.auth.MjmlAuth
import es.atrujillo.mjml.service.definition.MjmlService
import es.atrujillo.mjml.util.logError
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpStatusCodeException

/**
 * Service implementation to convert a MJML template to HTML through the API.
 * To instantiate this service we need a MjmlAuth instance.
 * @see MjmlAuth
 *
 * @throws es.atrujillo.mjml.exception.MjmlApiErrorException
 * @author Arnaldo Trujillo
 */
class MjmlRestService(private val authConf: MjmlAuth) : MjmlService {

    override fun transpileMjmlToHtml(mjmlBody: String): String {

        val restClient = BasicAuthRestClient<MjmlRequest>(authConf.getMjmlApiEndpoint().toString(),
                authConf.getMjmlApplicationId(), authConf.getMjmlApplicationSecretKey())

        val request = MjmlRequest(mjmlBody)

        try {
            val responseEntity = restClient.post(request, TRANSPILE_RENDER_MJML_PATH, object : ParameterizedTypeReference<MjmlResponse>() {})
            if (responseEntity.statusCode.is2xxSuccessful) {
                val response: MjmlResponse? = responseEntity.let { response: ResponseEntity<MjmlResponse> -> response.body }
                if (response != null) {
                    validateMjmlVersion(mjmlBody, response)
                    return response.html
                }
                throw MjmlApiErrorException(MjmlApiError(EMPTY_RESPONSE_ERROR_MESSAGE), HttpStatus.NOT_FOUND)
            }

            throw MjmlApiErrorException(MjmlApiError(responseEntity.statusCode.reasonPhrase), responseEntity.statusCode)

        } catch (httpError: HttpStatusCodeException) {
            logError(httpError.localizedMessage, httpError)
            val mjmlApiError = restClient.getObjectMapper().readValue<MjmlApiError>(httpError.responseBodyAsString)
            throw MjmlApiErrorException(mjmlApiError, httpError.statusCode)
        }
    }

    private fun validateMjmlVersion(requestBody: String, response: MjmlResponse) {
        if (requestBody.contains(DEPRECATED_MJML_ELEMENT) && response.mjmlVersion >= 4.0)
            throw MjmlApiErrorException(MjmlApiError("Sended MJML template is invalid with current API version (${response.mjmlVersion}). " +
                    "Please, use a valid ${response.mjmlVersion} template."), HttpStatus.BAD_REQUEST)
    }

    companion object {
        private const val TRANSPILE_RENDER_MJML_PATH = "/render"
        private const val DEPRECATED_MJML_ELEMENT = "mj-container"
        private const val EMPTY_RESPONSE_ERROR_MESSAGE = "Not response body found"
    }
}
