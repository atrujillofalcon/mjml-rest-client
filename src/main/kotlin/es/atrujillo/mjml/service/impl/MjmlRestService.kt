package es.atrujillo.mjml.service.impl

import es.atrujillo.mjml.exception.MjmlApiErrorException
import es.atrujillo.mjml.exception.MjmlApiUnsupportedVersionException
import es.atrujillo.mjml.model.mjml.MjmlApiError
import es.atrujillo.mjml.model.mjml.MjmlRequest
import es.atrujillo.mjml.model.mjml.MjmlResponse
import es.atrujillo.mjml.rest.BasicAuthRestClient
import es.atrujillo.mjml.rest.RestClient
import es.atrujillo.mjml.service.auth.MjmlAuth
import es.atrujillo.mjml.service.definition.MjmlService
import es.atrujillo.mjml.util.logWarn
import okhttp3.OkHttpClient
import java.time.Duration

/** Synchronous MJML service. An injected OkHttp client remains owned by its caller. */
class MjmlRestService private constructor(private val restClient: RestClient) : MjmlService {
    constructor(authConf: MjmlAuth) : this(authConf, BasicAuthRestClient.DEFAULT_CLIENT, Duration.ofSeconds(30))

    constructor(authConf: MjmlAuth, httpClient: OkHttpClient, requestTimeout: Duration) :
        this(BasicAuthRestClient(authConf, httpClient, requestTimeout))

    override fun transpileMjmlToHtml(mjmlBody: String): String {
        val response = restClient.post("/render", MjmlRequest(mjmlBody), MjmlResponse::class.java)
        val result = response.body
        if (result.html.isEmpty())
            throw MjmlApiErrorException(MjmlApiError("Missing HTML in API response"), response.statusCode)
        if (result.getMajorVersion() < 4 && !mjmlBody.contains("mj-container"))
            throw MjmlApiUnsupportedVersionException(result.mjmlVersion)
        result.errors.forEach { logWarn(it.formattedMessage) }
        return result.html
    }
}
