package es.atrujillo.mjml.model.mjml

import com.fasterxml.jackson.annotation.JsonProperty
import es.atrujillo.mjml.util.StringConstants

internal data class MjmlResponse(val html: String = StringConstants.EMPTY,
                                 val mjml: String = StringConstants.EMPTY,
                                 @JsonProperty("mjml_version")
                                 val mjmlVersion: String = StringConstants.EMPTY,
                                 val errors: List<MjmlError> = emptyList()) {

    fun getMajorVersion(): Double {
        return if (mjmlVersion.contains(".")) mjmlVersion
                .substring(0..mjmlVersion.indexOfFirst { c -> c.equals('.', true) })
                .toDouble() else 0.0
    }
}