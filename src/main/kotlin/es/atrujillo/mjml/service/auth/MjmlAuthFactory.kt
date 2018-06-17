package es.atrujillo.mjml.service.auth

import java.net.URI
import java.util.*

/**
 * Factory class that returns a instance of MjmlAuth.
 * This factory implements the Step Pattern that allows us to create
 * all types subclasses of MjmlAuth in an orderly and clear manner.
 *
 * MjmlAuth types can be:
 * @see SystemEnvironmentMjmlAuth
 * @see PropertiesMjmlAuth
 * @see MemoryMjmlAuth
 *
 * @example
 *
 *  MjmlAuth propertyAuthConf = MjmlAuthFactory.builder()
 *                              .withPropertiesCredential()
 *                              .properties(propertiesFile)
 *                              .mjmlKeyNames(appPropKey, secretPropKey)
 *                              .build();
 *
 * @return MjmlAuth
 * @author Arnaldo Trujillo
 */
class MjmlAuthFactory private constructor(){

    interface ChooseTypeStep {
        /**
         * First step to configure a {@link SystemEnvironmentMjmlAuth}
         * @return Next environment auth step
         */
        fun withEnvironmentCredentials(): EnvAuthStep

        /**
         * First step to configure a {@link MemoryMjmlAuth}
         * @return Next in memory auth step
         */
        fun withMemoryCredentials(): MemoryAuthStep

        /**
         * First step to configure a {@link PropertiesMjmlAuth}
         * @return Next file properties auth step
         */
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
        /**
         * Return the MjmlAuth instance when configuration is finished
         */
        fun build(): MjmlAuth

        /**
         * Change the default MJML Api endpoint
         */
        fun changeEndpoint(endpoint: URI): BuildStep
    }

    companion object {

        private enum class AuthType { MEMORY, PROPERTIES, ENV }

        /**
         * Init method to create the MjmlAuth instance
         * @return Builder
         */
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