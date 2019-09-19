# Mjml Rest Client

[![ko-fi](https://www.ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/W7W1UXQE)

[![CircleCI](https://circleci.com/gh/atrujillofalcon/mjml-rest-client.svg?style=svg)](https://circleci.com/gh/atrujillofalcon/mjml-rest-client)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/38e786b08ee544ec81e4dffc1fc3e5dd)](https://app.codacy.com/app/atrujillo92work/mjml-rest-client?utm_source=github.com&utm_medium=referral&utm_content=atrujillofalcon/mjml-rest-client&utm_campaign=badger)
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
    <version>2.0.0</version>
</dependency>
```

**Gradle**:
```groovy
compile "es.atrujillo.mjml:mjml-rest-client:2.0.0"
```

## Usage


### Creating templates

This library includes Thymleaf engine to allow to create dynamic templates before to send to Mjml API.

We have two options for templating mjml mails. In-memory String or File.

#### File templates

Now we're going to see how create the template from a file source to create a fun mail. Let's imagine that we have a Thymeleaf template in a file called readme-template.mjml with the following content:

```xml
<mjml>
    <mj-body>
        <mj-container>
            <mj-section>
                <mj-column>
                    <mj-text font-style="bold" align="center" color="#8B9C36"><h1 th:text="${myTitle}"></h1></mj-text>
                    <mj-text font-style="bold" align="center" color="#8B9C36"><h3 th:text="${myDescription}"></h3></mj-text>
                    <mj-carousel>
                        <mj-carousel-image src="https://unblogdecode.es/gallery/dog1.jpg"/>
                        <mj-carousel-image src="https://unblogdecode.es/gallery/dog2.jpg"/>
                        <mj-carousel-image src="https://unblogdecode.es/gallery/dog3.jpg"/>
                    </mj-carousel>
                </mj-column>
            </mj-section>
        </mj-container>
    </mj-body>
</mjml>
```

If we look, we have two variables: **myTitle** and **myDescription** that we're going to replace dynamically. Let's see how use the File Template mode:

```java
File fileTemplate = new File("/path/to/mjml/readme-template.mjml");
Context contextVars = new Context();
contextVars.setVariable("myTitle","Dog Gallery");
contextVars.setVariable("message","This is my dog Bilbo, modeling for the camera");
       
String mjmlTemplate = TemplateFactory.builder()
               .type(TemplateType.FILE)
               .template(fileTemplate)
               .templateContext(contextVars)
               .buildTemplate();                
 ```
 
**Final Result of Template**

![Mjml Screenshoot](https://unblogdecode.es/gallery/mjm-screenshoot.png)
 
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
MjmlService mjmlService = new MjmlRestService(authConfInstance);

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

