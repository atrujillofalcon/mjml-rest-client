# Mjml Rest Client

Mjml is the best responsive mail framework, I love it :heart:. I created this project to have a Java library that use the
**mjml API** to convert a mjml template into valid html ready to use.


## Built With

* [Kotlin](https://kotlinlang.org/) - The future of JVM languages
* [Gradle](https://kotlinlang.org/) - Dependency Management
* [Spring Rest Template](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html) - Used to communicate with API
* [Jackson](https://github.com/FasterXML/jackson-databind) - Best databind Java library


### Installing

To include this library in your project you first have to configure jcenter repository. 

If your project is built with Maven you have to add this to pom.xml:

```
<repositories>
    <repository>
      <id>jcenter</id>
      <url>https://jcenter.bintray.com/</url>
    </repository>
</repositories>

```

In Gradle we have to indicate jcenter repository inside of buildscript:

```
buildscript {
    repositories {
        jcenter()
    }
}
```

After configure jcenter repository we only need to add the library as dependency:

**Maven**:
```
<dependency>
            <groupId>es.atrujillo.mjml</groupId>
            <artifactId>mjml-rest-client</artifactId>
            <version>1.0.5</version>
        </dependency>
```

**Gradle**:
```
compile "es.atrujillo.mjml:mjml-rest-client:1.0.5"
```

## Usage

Import the library into your project. Configure the MjmlApi credentials for your project and instantiate a MjmlService object

```
MjmlAuthConf authConf = new MemoryMjmlAuthConf(applicationId, secretKey);
MjmlService mjmlService = new MjmlRestService(authConf);

String resultHtmlMail = mjmlService.transpileMjmlToHtml(mjmlTemplate);
//after obtain the html you can send it using your email service implementation.
```


## Running the tests

Execute from root folder:

```
gradle test
```

## Author

 [**Arnaldo Trujillo**](https://github.com/atrujillofalcon)

See also the list of [contributors](https://github.com/atrujillofalcon/mjml-rest-client/graphs/contributors) who participated in this project.

## License

This project is licensed under the Apache License - see the [LICENSE.md](LICENSE.md) file for details

