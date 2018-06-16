package es.atrujillo.mjml.model.mjml

/**
 * API Errors to not 200 responses
 */
data class MjmlApiError(val message: String/*,
                        @JsonProperty("request_id") val requestId: String,
                        @JsonProperty("started_at") val startedAt: LocalDate*/)