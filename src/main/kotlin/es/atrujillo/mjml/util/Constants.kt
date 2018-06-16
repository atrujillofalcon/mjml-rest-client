package es.atrujillo.mjml.util

/**
 * String common constants
 *
 * @author Arnaldo Trujillo
 */
internal class StringConstants {

    companion object {
        const val EMPTY = ""
    }

}

/**
 * Regex common expressions
 *
 * @author Arnaldo Trujillo
 */
internal class RegexConstants {

    companion object {
        @JvmStatic
        val URL_REGEX = Regex("^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
    }

}