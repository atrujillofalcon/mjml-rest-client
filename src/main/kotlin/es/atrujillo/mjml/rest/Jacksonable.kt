package es.atrujillo.mjml.rest

import com.fasterxml.jackson.databind.ObjectMapper

/**
 * @author Arnaldo Trujillo
 */
interface Jacksonable {

    fun getObjectMapper(): ObjectMapper
}