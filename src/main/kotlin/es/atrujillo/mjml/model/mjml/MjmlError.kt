package es.atrujillo.mjml.model.mjml

internal data class MjmlError(val message: String, val tagName: String,
                              val formattedMessage: String, @com.fasterxml.jackson.annotation.JsonProperty("line") val lineNumber: Int? = null)