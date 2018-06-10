package es.atrujillo.mjml.config.template

import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.context.IContext
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.FileTemplateResolver
import org.thymeleaf.templateresolver.StringTemplateResolver
import java.io.File

class TemplateFactory private constructor(templateEngine: TemplateEngine) {


    interface TemplateTypeStep {
        fun type(type: TemplateType): BuildStep
    }

    interface BuildStep {

        fun buildTemplate(): String

        fun templateContext(templateContext: IContext): BuildStep

        fun template(fileTemplate: File): BuildStep
        fun template(stringTemplate: String): BuildStep

    }

    companion object {

        @JvmStatic
        fun builder(): TemplateTypeStep = Builder()

        class Builder : TemplateTypeStep, BuildStep {

            private lateinit var templateType: TemplateType
            private var context: IContext = Context()
            private var fileTemplate: File? = null
            private var stringTemplate: String? = null


            override fun type(type: TemplateType): BuildStep {
                this.templateType = type
                return this
            }

            override fun buildTemplate(): String {
                val engine = TemplateEngine()
                val template: String
                val resolver = when (templateType) {
                    TemplateType.STRING -> {
                        if (stringTemplate.isNullOrEmpty())
                            throw IllegalArgumentException("Enter a valid String template")

                        template = stringTemplate!!
                        val resolver = StringTemplateResolver()
                        resolver.templateMode = TemplateMode.XML
                        resolver
                    }
                    TemplateType.FILE -> {
                        if (fileTemplate != null && fileTemplate!!.exists() && fileTemplate!!.isFile) {
                            val resolver = FileTemplateResolver()
                            resolver.templateMode = TemplateMode.XML
                            resolver.prefix = "${fileTemplate!!.parent}/"
                            resolver.suffix = ".${fileTemplate!!.extension}"
                            template = fileTemplate!!.nameWithoutExtension

                            resolver
                        } else {
                            throw IllegalArgumentException("Enter a valid File template")
                        }
                    }
                }

                engine.setTemplateResolver(resolver)
                return engine.process(template, context)
            }

            override fun templateContext(templateContext: IContext): BuildStep {
                this.context = templateContext
                return this
            }

            override fun template(fileTemplate: File): BuildStep {
                this.fileTemplate = fileTemplate
                return this
            }

            override fun template(stringTemplate: String): BuildStep {
                this.stringTemplate = stringTemplate
                return this
            }
        }
    }
}