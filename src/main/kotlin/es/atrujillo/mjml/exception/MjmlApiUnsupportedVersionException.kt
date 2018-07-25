package es.atrujillo.mjml.exception

import es.atrujillo.mjml.model.mjml.MjmlApiError
import org.springframework.http.HttpStatus

/**
 * Exception throwed when mjml template is unsupported by API mjml version
 */
class MjmlApiUnsupportedVersionException(mjmlVersion: String)
    : MjmlApiErrorException(MjmlApiError("Sended MJML template is invalid with current API version ($mjmlVersion). " +
        "Please, use a valid $mjmlVersion supported template."), HttpStatus.BAD_REQUEST)