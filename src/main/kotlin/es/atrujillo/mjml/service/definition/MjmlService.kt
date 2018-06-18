package es.atrujillo.mjml.service.definition

/**
 * Service to convert a template in MJML format to the final HTML that we will use in our emails.
 * @see es.atrujillo.mjml.service.impl.MjmlRestService
 *
 *
 * @throws es.atrujillo.mjml.exception.MjmlApiErrorException
 * @author Arnaldo Trujillo
 */
interface MjmlService {

    /**
     * Convert the mjml template to html
     * @param mjmlBody Mjml template body
     * @return Transpiled final HTML
     */
    fun transpileMjmlToHtml(mjmlBody: String): String
}
