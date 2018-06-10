package es.atrujillo.mjml.service.impl

import es.atrujillo.mjml.model.mjml.MjmlRequest
import es.atrujillo.mjml.model.mjml.MjmlResponse
import es.atrujillo.mjml.rest.BasicAuthRestClient
import es.atrujillo.mjml.service.auth.MjmlAuth
import es.atrujillo.mjml.service.definition.MjmlService
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.ResponseEntity
import java.util.*

/**
 * @author Arnaldo Trujillo
 */
class MjmlRestService(private val authConf: MjmlAuth) : MjmlService {

    override fun transpileMjmlToHtml(mjmlBody: String): String {

        val restClient = BasicAuthRestClient<MjmlRequest>(authConf.getMjmlApiEndpoint().toString(),
                authConf.getMjmlApplicationId(), authConf.getMjmlApplicationSecretKey())

        val request = MjmlRequest(mjmlBody)

        return Optional.of(restClient.post(request, TRANSPILE_RENDER_MJML_PATH, object : ParameterizedTypeReference<MjmlResponse>() {}, null, null))
                .filter { mjmlResponseEntity -> mjmlResponseEntity.statusCode.is2xxSuccessful }
                .map { response: ResponseEntity<MjmlResponse>? -> response!!.body }
                .map { body -> body.html }
                .orElse("")
    }

    companion object {
        private const val TRANSPILE_RENDER_MJML_PATH = "/render"
    }
}
