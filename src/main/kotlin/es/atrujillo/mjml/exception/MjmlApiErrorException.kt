package es.atrujillo.mjml.exception

import es.atrujillo.mjml.model.mjml.MjmlApiError
import org.springframework.http.HttpStatus

class MjmlApiErrorException(mjmlApiError: MjmlApiError, statusCode: HttpStatus)
    : RuntimeException("API Error ${statusCode.value()} - ${mjmlApiError.message}")