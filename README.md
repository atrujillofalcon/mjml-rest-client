# MJML Rest Client

Java/Kotlin library that converts MJML to email HTML through the MJML API using OkHttp. Includes Thymeleaf templates and requires no Spring dependencies.

Requires **JDK 21**. Build and run local tests without API credentials:

```sh
./gradlew clean build
```

Coordinates: `es.atrujillo.mjml:mjml-rest-client`. The current development version is `3.0.0-SNAPSHOT`; build and install it locally with `./gradlew publishToMavenLocal`.

```java
MjmlAuth auth = MjmlAuthFactory.builder()
    .withEnvironmentCredentials()
    .mjmlKeyNames("MJML_APP_ID", "MJML_SECRET_KEY")
    .build();
MjmlService client = new MjmlRestService(auth);
String html = client.transpileMjmlToHtml(
    "<mjml><mj-body><mj-section><mj-column><mj-text>Hello!</mj-text>"
    + "</mj-column></mj-section></mj-body></mjml>");
```

Use `TemplateFactory.builder().withStringTemplate()` or `.withFileTemplate()` to prepare dynamic MJML with Thymeleaf before rendering.

[Migration and configuration](docs/migration.md) · [Publishing](docs/publishing.md) · [Apache 2.0 license](LICENSE.md)
