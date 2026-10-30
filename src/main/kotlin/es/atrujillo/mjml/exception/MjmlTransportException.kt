package es.atrujillo.mjml.exception

/** Communication failure; [cause] retains the underlying IO or interruption failure. */
class MjmlTransportException(message: String, cause: Throwable) : RuntimeException(message, cause)
