package es.atrujillo.mjml.rest

import com.fasterxml.jackson.databind.ObjectMapper

/**
 * @author Arnaldo Trujillo
 */
internal interface Jacksonable {

    fun getObjectMapper(): ObjectMapper
}