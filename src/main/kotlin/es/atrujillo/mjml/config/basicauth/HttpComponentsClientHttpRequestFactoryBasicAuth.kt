package es.atrujillo.mjml.config.basicauth

import org.apache.http.HttpHost
import org.apache.http.client.protocol.HttpClientContext
import org.apache.http.impl.auth.BasicScheme
import org.apache.http.impl.client.BasicAuthCache
import org.apache.http.protocol.BasicHttpContext
import org.apache.http.protocol.HttpContext
import org.springframework.http.HttpMethod
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import java.net.URI


class HttpComponentsClientHttpRequestFactoryBasicAuth(private val host: HttpHost) : HttpComponentsClientHttpRequestFactory() {

    override fun createHttpContext(httpMethod: HttpMethod?, uri: URI?) = createHttpContext()

    private fun createHttpContext(): HttpContext {
        val authCache = BasicAuthCache()
        val basicAuth = BasicScheme()
        authCache.put(host, basicAuth)

        val localcontext = BasicHttpContext()
        localcontext.setAttribute(HttpClientContext.AUTH_CACHE, authCache)
        return localcontext
    }

}
