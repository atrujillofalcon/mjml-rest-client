# Mjml Rest Client

[![CircleCI](https://circleci.com/gh/atrujillofalcon/mjml-rest-client.svg?style=svg)](https://circleci.com/gh/atrujillofalcon/mjml-rest-client)
[![codecov](https://codecov.io/gh/atrujillofalcon/mjml-rest-client/branch/develop/graph/badge.svg)](https://codecov.io/gh/atrujillofalcon/mjml-rest-client)



[Mjml](https://mjml.io/) is the best responsive mail framework, I love it :heart:. I created this project to have a Java library that use the
**mjml API** to convert a mjml template into valid html ready to use.


## Built With

* [Kotlin](https://kotlinlang.org/) - The future of JVM languages
* [Gradle](https://gradle.org/) - Dependency Management
* [Spring Rest Template](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html) - Used to communicate with API
* [Jackson](https://github.com/FasterXML/jackson-databind) - Best databind Java library
* [Thymeleaf](https://www.thymeleaf.org/) - Template Engine


### Installing

To include this library into your project your only need to add the dependency.

**Maven**:
```xml
<dependency>
    <groupId>es.atrujillo.mjml</groupId>
    <artifactId>mjml-rest-client</artifactId>
    <version>1.4.4</version>
</dependency>
```

**Gradle**:
```groovy
compile "es.atrujillo.mjml:mjml-rest-client:1.4.4"
```

## Usage


### Creating templates

This library includes Thymleaf engine to allow to create dynamic templates before to send to Mjml API.

We have two options for templating mjml mails. In-memory String or File.

#### File templates

Lets see how create the template from a file source

```java
File fileTemplate = new File("/path/to/mjml/template.xml");
Context contextVars = new Context();
contextVars.setVariable("message","Hello MJML");
       
String mjmlTemplate = TemplateFactory.builder()
               .type(TemplateType.FILE)
               .template(fileTemplate)
               .templateContext(contextVars)
               .buildTemplate();                
 ```
#### In-Memory String templates

```java
private static final String DUMMY_TEMPLATE = "<mjml><mj-body><mj-container><mj-section><mj-column><mj-text th:text=\"${message}\"></mj-text></mj-column></mj-section></mj-container></mj-body></mjml>";
...
...
...
Context contextVars = new Context();
contextVars.setVariable("message","Hello MJML");

String mjmlTemplate = TemplateFactory.builder()
                .withStringTemplate()
                .template(DUMMY_TEMPLATE)
                .templateContext(contextVars)
                .buildTemplate();              
 ```

### API Credentials

We already have the template, but before to call to API we need the API credentials to initialize our service client.

You can obtain the credentials [**here**](https://mjml.io/api).

Before calling our service we have to create an instance of MjmlAuth through the MjmlAuthFactory class.
 We have three options:
 
```java
//Get credentials from environments variables
MjmlAuth systemEnvAuthConf = MjmlAuthFactory.builder()
                .withEnvironmentCredentials()
                .mjmlKeyNames(MJML_APP_ID, MJML_SECRET_KEY)
                .build();

//Enter in-memory string credentials directly into auth factory
MjmlAuth memoryAuthConf = MjmlAuthFactory.builder()
                .withMemoryCredentials()
                .mjmlCredentials(mjmlAppId, mjmlSecretKey)
                .build();

//Get credentials from properties file
MjmlAuth propertyAuthConf = MjmlAuthFactory.builder()
                .withPropertiesCredential()
                .properties(propertiesFile)
                .mjmlKeyNames(appPropKey, secretPropKey)
                .build();
```

### Obtaining final HTML

Finally, we just need to instantiate our client with the credentials obtained
 and use it to convert the template into the final HTML to send it to whoever we want.

```java
MjmlAuthConf authConf = new MemoryMjmlAuthConf(applicationId, secretKey);
MjmlService mjmlService = new MjmlRestService(authConf);

String resultHtmlMail = mjmlService.transpileMjmlToHtml(mjmlTemplate);
//after obtain the html you can send it using your email service implementation.
```


## Running the tests

First you have to set **MJML_APP_ID** and **MJML_SECRET_KEY** environment variables.

Execute from root folder:

```groovy
gradle test
```

## Author

 [**Arnaldo Trujillo**](https://github.com/atrujillofalcon)

See also the list of [contributors](https://github.com/atrujillofalcon/mjml-rest-client/graphs/contributors) who participated in this project.

## License

This project is licensed under the Apache License - see the [LICENSE.md](LICENSE.md) file for details

