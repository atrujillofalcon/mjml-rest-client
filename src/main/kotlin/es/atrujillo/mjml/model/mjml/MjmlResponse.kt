package es.atrujillo.mjml.model.mjml

import es.atrujillo.mjml.model.mjml.MjmlError

class MjmlResponse(val html: String = "", val mjml: String = "", val mjml_version: String = "",
                   val errors: List<MjmlError> = emptyList())