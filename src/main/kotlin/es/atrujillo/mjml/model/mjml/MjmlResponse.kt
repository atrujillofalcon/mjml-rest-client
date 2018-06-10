package es.atrujillo.mjml.model.mjml

internal class MjmlResponse(val html: String = "", val mjml: String = "", val mjml_version: String = "",
                   val errors: List<MjmlError> = emptyList())