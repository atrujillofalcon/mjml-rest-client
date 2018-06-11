package es.atrujillo.mjml.util

class RegexConstants {

    companion object {

        @JvmStatic
        val URL_REGEX = Regex("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
    }

}