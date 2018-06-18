package es.atrujillo.mjml.exception

/**
 * Exception throwed when entered URL don't match with a URL regex pattern
 * @see es.atrujillo.mjml.util.RegexConstants.URL_REGEX
 */
class InvalidMjmlApiUrlException : IllegalArgumentException("Invalid MJML API url entered")