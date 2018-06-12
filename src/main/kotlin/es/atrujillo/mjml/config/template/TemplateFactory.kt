package es.atrujillo.mjml.config.template

import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.context.IContext
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.FileTemplateResolver
import org.thymeleaf.templateresolver.StringTemplateResolver
import java.io.File

/**
 * Factory class that allow renderize Thymleaf template dinamically.
 * The origin template can be a File or a String
 * @see es.atrujillo.mjml.config.template.TemplateType
 *
 * This creational class use the Step Pattern.
 *
 *
 * @author Arnaldo Trujillo
 */
class TemplateFactory private constructor(templateEngine: TemplateEngine) {

    interface ChooseTypeStep {

        /**
         * Choose File template mode.
         * @return TemplateFileStep
         */
        fun withFileTemplate(): TemplateFileStep

        /**
         * Choose String template mode.
         * @return TemplateStringStep
         */
        fun withStringTemplate(): TemplateStringStep
    }

    interface TemplateFileStep {

        /**
         * Set File template.
         * Should be a valid existing File
         */
        fun template(fileTemplate: File): BuildStep
    }

    interface TemplateStringStep {

        /**
         * Set String template.
         * Can't be empty nor null.
         */
        fun template(stringTemplate: String): BuildStep

    }

    interface BuildStep {

        fun buildTemplate(): String

        fun templateContext(templateContext: IContext): BuildStep

    }

    companion object {

        /**
         * Builder static method
         */
        @JvmStatic
        fun builder(): ChooseTypeStep = Builder()

        class Builder : ChooseTypeStep, TemplateStringStep, TemplateFileStep, BuildStep {

            private lateinit var templateType: TemplateType
            private var context: IContext = Context()
            private lateinit var fileTemplate: File
            private lateinit var stringTemplate: String

            override fun withFileTemplate(): TemplateFileStep {
                templateType = TemplateType.FILE
                return this
            }

            override fun withStringTemplate(): TemplateStringStep {
                templateType = TemplateType.STRING
                return this
            }

            override fun buildTemplate(): String {
                val engine = TemplateEngine()
                val template: String
                val resolver = when (templateType) {
                    TemplateType.STRING -> {
                        if (stringTemplate.isEmpty())
                            throw IllegalArgumentException("Enter a valid String template")

                        template = stringTemplate
                        val resolver = StringTemplateResolver()
                        resolver.templateMode = TemplateMode.XML
                        resolver
                    }
                    TemplateType.FILE -> {
                        if (fileTemplate.exists() && fileTemplate.isFile) {
                            val resolver = FileTemplateResolver()
                            resolver.templateMode = TemplateMode.XML
                            resolver.prefix = "${fileTemplate.parent}/"
                            resolver.suffix = ".${fileTemplate.extension}"
                            template = fileTemplate.nameWithoutExtension

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