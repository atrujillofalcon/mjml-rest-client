package es.atrujillo.mjml.util

internal class RegexConstants {

    companion object {

        @JvmStatic
        val URL_REGEX = Regex("^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
    }

}