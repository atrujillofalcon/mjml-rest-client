package es.atrujillo.mjml.model.mjml

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

/**
 * API Errors to not 200 responses
 */
data class MjmlApiError(val message: String,
                        @JsonProperty("request_id") val requestId: String,
                        @JsonProperty("started_at") val startedAt: LocalDate)