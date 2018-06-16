package es.atrujillo.mjml.service.impl

import es.atrujillo.mjml.exception.MjmlApiErrorException
import es.atrujillo.mjml.model.mjml.MjmlApiError
import es.atrujillo.mjml.model.mjml.MjmlRequest
import es.atrujillo.mjml.model.mjml.MjmlResponse
import es.atrujillo.mjml.rest.BasicAuthRestClient
import es.atrujillo.mjml.service.auth.MjmlAuth
import es.atrujillo.mjml.service.definition.MjmlService
import es.atrujillo.mjml.util.StringConstants
import es.atrujillo.mjml.util.logError
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpStatusCodeException

/**
 * Service implementation to convert a MJML template to HTML through the API.
 * To instantiate this service we need a MjmlAuth instance.
 * @see MjmlAuth
 *
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
                return responseEntity
                        .let { response: ResponseEntity<MjmlResponse> -> response.body }
                        ?.let { body -> body.html } ?: StringConstants.EMPTY
            }

            throw MjmlApiErrorException(MjmlApiError(responseEntity.statusCode.reasonPhrase), responseEntity.statusCode)

        } catch (httpError: HttpStatusCodeException) {
            logError(httpError.localizedMessage, httpError)
//          val mjmlApiError = restClient.getObjectMapper().readValue<MjmlApiError>(httpError.responseBodyAsString)
            val mjmlApiError = MjmlApiError(httpError.responseBodyAsString)
            throw MjmlApiErrorException(mjmlApiError, httpError.statusCode)
        }
    }

    companion object {
        private const val TRANSPILE_RENDER_MJML_PATH = "/render"
    }
}
