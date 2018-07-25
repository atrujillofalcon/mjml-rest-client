package es.atrujillo.mjml.config.template

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.thymeleaf.context.Context
import java.io.File
import java.net.URL
import java.util.*

internal class TemplateFactoryTest {

    @Test
    @DisplayName("Test Build String Template")
    internal fun testBuildStringTemplate() {
        val template = TemplateFactory.builder()
                .withStringTemplate()
                .template(HELLO_WORLD_MJML)
                .buildTemplate()

        Assertions.assertEquals(HELLO_WORLD_MJML, template, "The built template has to be the same")
    }

    @Test
    @DisplayName("Test that build fails if template is not set")
    internal fun whenBuildStringTemplateWithoutSetTemplate() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            TemplateFactory.builder()
                    .withStringTemplate()
                    .template("")
                    .buildTemplate()
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            TemplateFactory.builder()
                    .withFileTemplate()
                    .template(File("/invalid/path/file"))
                    .buildTemplate()
        }
    }

    @Test
    @DisplayName("Test Build File Template With Context")
    internal fun testBuildFileTemplate() {
        val fileTemplate = File(
                Objects.requireNonNull<URL>(javaClass.classLoader.getResource("template/test_template.xml"))
                        .file)

        val context = Context(Locale.getDefault())
        val messageVal = "Hello World"
        context.setVariable("message", messageVal)
        context.setVariable("number", 1)

        val template = TemplateFactory.builder()
                .withFileTemplate()
                .template(fileTemplate)
                .templateContext(context)
                .buildTemplate()

        Assertions.assertNotNull(template)
        Assertions.assertTrue(template.isNotEmpty(), "Templace can't be empty")
        Assertions.assertTrue(template.contains(messageVal), "Template has to include message variable")
        Assertions.assertTrue(template.contains("Positive Column"), "Template has to include positive column")
        Assertions.assertFalse(template.contains("Negative Column"), "Template can't include negative column")
    }

    @Test
    @DisplayName("Test Build File Template With Context")
    internal fun whenBuildFileTemplateWithIvalidFilePath() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            val invalidFile = File("/invalid/path/template.xml")

            TemplateFactory.builder()
                    .withFileTemplate()
                    .template(invalidFile)
                    .buildTemplate()
        }
    }

    companion object {
        private const val HELLO_WORLD_MJML = "<mjml><mj-body><mj-container><mj-section><mj-column><mj-text>Hello World</mj-text></mj-column></mj-section></mj-container></mj-body></mjml>"
    }

}
