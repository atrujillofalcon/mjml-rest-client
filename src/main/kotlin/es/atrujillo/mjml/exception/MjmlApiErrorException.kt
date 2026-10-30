package es.atrujillo.mjml.exception

import es.atrujillo.mjml.model.mjml.MjmlApiError

/** API error retaining the HTTP status and structured MJML error. */
open class MjmlApiErrorException(val mjmlApiError: MjmlApiError, val statusCode: Int)
    : RuntimeException("API Error $statusCode - ${mjmlApiError.message}")
