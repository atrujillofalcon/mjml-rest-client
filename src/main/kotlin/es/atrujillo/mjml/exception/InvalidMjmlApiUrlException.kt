package es.atrujillo.mjml.exception

/** The endpoint must be an absolute HTTP(S) URI without user info, query or fragment. */
class InvalidMjmlApiUrlException : IllegalArgumentException("Invalid MJML API url entered")
