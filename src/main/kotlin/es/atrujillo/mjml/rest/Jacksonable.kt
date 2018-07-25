package es.atrujillo.mjml.rest

import com.fasterxml.jackson.databind.ObjectMapper

/**
 * Classes that implements this interface should be capables of obtain a {@link ObjectMapper} from its context.
 * @see ObjectMapper
 *
 * @author Arnaldo Trujillo
 */
internal interface Jacksonable {

    /**
     * Return the previusly configured ObjectMapper of library context
     *
     * @see ObjectMapper
     * @return ObjectMapper
     */
    fun getObjectMapper(): ObjectMapper
}