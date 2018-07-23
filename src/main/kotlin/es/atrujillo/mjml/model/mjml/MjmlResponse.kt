package es.atrujillo.mjml.model.mjml

import com.fasterxml.jackson.annotation.JsonProperty
import es.atrujillo.mjml.util.StringConstants

internal data class MjmlResponse(val html: String = StringConstants.EMPTY,
                                 val mjml: String = StringConstants.EMPTY,
                                 @JsonProperty("mjml_version")
                                 val mjmlVersion: Double = 0.0,
                                 val errors: List<MjmlError> = emptyList())