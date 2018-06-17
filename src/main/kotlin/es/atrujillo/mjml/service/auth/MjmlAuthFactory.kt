package es.atrujillo.mjml.service.auth

import java.net.URI
import java.util.*

class MjmlAuthFactory {

    interface ChooseTypeStep {
        fun withEnvironmentCredentials(): EnvAuthStep
        fun withMemoryCredentials(): MemoryAuthStep
        fun withPropertiesCredential(): PropertiesAuthStep
    }

    interface MemoryAuthStep {
        fun mjmlCredentials(mjmlAppId: String, mjmlSecretKey: String): BuildStep
    }

    interface PropertiesAuthStep {
        fun properties(properties: Properties): EnvAuthStep
    }

    interface EnvAuthStep {
        fun mjmlKeyNames(mjmlAppIdKeyName: String, mjmlSecretKeyName: String): BuildStep
    }

    interface BuildStep {

        fun build(): MjmlAuth

        fun changeEndpoint(endpoint: URI): BuildStep

    }

    companion object {

        private enum class AuthType { MEMORY, PROPERTIES, ENV }

        @JvmStatic
        fun builder(): ChooseTypeStep = Builder()

        class Builder : ChooseTypeStep, MemoryAuthStep, PropertiesAuthStep, EnvAuthStep, BuildStep {

            private lateinit var authType: AuthType
            private lateinit var mjmlAppId: String
            private lateinit var mjmlSecretKey: String
            private lateinit var appIdKeyName: String
            private lateinit var secretKeyName: String
            private lateinit var properties: Properties
            private var endpoint: URI = URI.create("https://api.mjml.io/v1")

            override fun withEnvironmentCredentials(): EnvAuthStep {
                authType = AuthType.ENV
                return this
            }

            override fun withMemoryCredentials(): MemoryAuthStep {
                authType = AuthType.MEMORY
                return this
            }

            override fun withPropertiesCredential(): PropertiesAuthStep {
                authType = AuthType.PROPERTIES
                return this
            }

            override fun mjmlCredentials(mjmlAppId: String, mjmlSecretKey: String): BuildStep {
                this.mjmlAppId = mjmlAppId
                this.mjmlSecretKey = mjmlSecretKey
                return this
            }

            override fun properties(properties: Properties): EnvAuthStep {
                this.properties = properties
                return this
            }

            override fun mjmlKeyNames(mjmlAppIdKeyName: String, mjmlSecretKeyName: String): BuildStep {
                this.appIdKeyName = mjmlAppIdKeyName
                this.secretKeyName = mjmlSecretKeyName
                return this
            }

            override fun build(): MjmlAuth {
                return when (authType) {
                    AuthType.MEMORY -> MemoryMjmlAuth(mjmlAppId, mjmlSecretKey, endpoint)
                    AuthType.PROPERTIES -> PropertiesMjmlAuth(properties, appIdKeyName, secretKeyName, endpoint)
                    AuthType.ENV -> SystemEnvironmentMjmlAuth(appIdKeyName, secretKeyName, endpoint)
                }
            }

            override fun changeEndpoint(endpoint: URI): BuildStep {
                this.endpoint = endpoint
                return this
            }

        }
    }

}