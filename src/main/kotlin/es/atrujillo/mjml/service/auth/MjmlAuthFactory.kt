package es.atrujillo.mjml.service.auth

import java.net.URI
import java.util.*

class MjmlAuthFactory {

    interface ChooseTypeStep {
        fun whitEnviromentCredentials(): EnvAuthStep
        fun whitMemoryCredentials(): MemoryAuthStep
        fun whitPropertiesCredential(): PropertiesAuthStep
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
            private var mjmlAppId: String? = null
            private var mjmlSecretKey: String? = null
            private var appIdKeyName: String? = null
            private var secretKeyName: String? = null
            private var endpoint: URI = URI.create("https://api.mjml.io/v1")
            private var properties: Properties? = null

            override fun whitEnviromentCredentials(): EnvAuthStep {
                authType = AuthType.ENV
                return this
            }

            override fun whitMemoryCredentials(): MemoryAuthStep {
                authType = AuthType.MEMORY
                return this
            }

            override fun whitPropertiesCredential(): PropertiesAuthStep {
                authType = AuthType.PROPERTIES
                return this
            }

            override fun mjmlCredentials(mjmlAppId: String, mjmlSecretKey: String): BuildStep {
                this.mjmlAppId = mjmlAppId
                this.mjmlSecretKey = mjmlSecretKey
                return this
            }

            override fun properties(properties: Properties): EnvAuthStep {
                this.properties = properties;
                return this
            }

            override fun mjmlKeyNames(mjmlAppIdKeyName: String, mjmlSecretKeyName: String): BuildStep {
                this.appIdKeyName = mjmlAppIdKeyName
                this.secretKeyName = mjmlSecretKeyName
                return this
            }

            override fun build(): MjmlAuth {
                return when (authType) {
                    AuthType.MEMORY -> MemoryMjmlAuth(mjmlAppId!!, mjmlSecretKey!!, endpoint)
                    AuthType.PROPERTIES -> PropertiesMjmlAuth(properties!!, appIdKeyName!!, secretKeyName!!, endpoint)
                    AuthType.ENV -> SystemEnvironmentMjmlAuth(appIdKeyName!!, secretKeyName!!, endpoint)
                }
            }

            override fun changeEndpoint(endpoint: URI): BuildStep {
                this.endpoint = endpoint
                return this
            }

        }
    }

}