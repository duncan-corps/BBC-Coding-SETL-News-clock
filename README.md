# BBC-Coding-SETL-News-clock
BBC SETL Coding test: Control and update News lower thirds clock.

# Setting up
You need a Java 17 development kit installed to compile the code. Instructions on how to do this are out-of-scope for this README, but there are several freely available builds of OpenJDK 17.

Whichever OS you are using probably has a prebuilt package installable from its package manager.

Further reading;
* [OpenJDK](https://openjdk.org/) (general information but emphasising Oracle's build);
  * [About OpenJDK 17](https://openjdk.org/projects/jdk/17/)
  * [Installing](https://openjdk.org/install/) (Linux-heavy)
* [Eclipse Temurin (AKA Adoptium)](https://projects.eclipse.org/projects/adoptium.temurin/downloads)

# The control app
To run the control app, at the command line;
```
./mvnw spring-boot:run
```
Among messages about the building of the app, you should see text like;
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.8)

2023-01-29 18:58:06.638  INFO 65934 --- [           main] uk.org.fwei.Application                  : Starting Application using Java 17.0.5 on mercury with PID 65934 (/home/dpc/Personal/Source/BBC-Coding-SETL-News-clock/target/classes started by dpc in /home/dpc/Personal/Source/BBC-Coding-SETL-News-clock)
2023-01-29 18:58:06.640  INFO 65934 --- [           main] uk.org.fwei.Application                  : No active profile set, falling back to 1 default profile: "default"
2023-01-29 18:58:07.305  INFO 65934 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2023-01-29 18:58:07.314  INFO 65934 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2023-01-29 18:58:07.314  INFO 65934 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.71]
2023-01-29 18:58:07.376  INFO 65934 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2023-01-29 18:58:07.376  INFO 65934 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 690 ms
Cannot send [CG 1 ADD 1 main/MAIN 1] to null:5250
Cannot send [CG 1 INVOKE 1 "leftTab('on', 'BBC News 18:58')"] to null:5250
2023-01-29 18:58:07.682  INFO 65934 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2023-01-29 18:58:07.694  INFO 65934 --- [           main] uk.org.fwei.Application                  : Started Application in 1.332 seconds (JVM running for 1.662)
Cannot send [CG 1 INVOKE 1 "leftTab('on', 'BBC News 18:59')"] to null:5250
Cannot send [CG 1 INVOKE 1 "leftTab('on', 'BBC News 19:00')"] to null:5250
```
Any messages about sending, not sending, or failing to send AMCP commands indicate that the control app is running as expected (even if it's having trouble connecting to a CasparCG Server).

If a problem occurs then a stack trace starting with text like; 
```
Cannot send [CG 1 INVOKE 1 "leftTab('on', 'Beeb News 18:55')"] to ccgs01:55555
java.net.UnknownHostException: ccgs01
	at java.base/sun.nio.ch.NioSocketImpl.connect(NioSocketImpl.java:567)
	at java.base/java.net.SocksSocketImpl.connect(SocksSocketImpl.java:327)
```
... will be output.

## Settings
Several settings can be configured at the command line; 
| Setting              | CLI option            | Default value | Notes                                             |
| -------------------- | --------------------- | ------------- | ------------------------------------------------- |
| CasparCG Server host | --casparCGServer.host | localhost     | use *null* for console output                     | 
| CasparCG Server host | --casparCGServer.port | 5250          |                                                   |
| Left tab text prefix | --leftTab.textPrefix  | 'BBC News'    | Use single quotes around values containing spaces |
### Examples
* The CasparCG Server host can be specified like this;
  ```
  ./mvnw spring-boot:run -Dspring-boot.run.arguments="--casparCGServer.host=ccgs01.fwei.org.uk"
  ```
* The Left tab's prefix text can be specified like this;
  ```
  ./mvnw spring-boot:run -Dspring-boot.run.arguments="--leftTab.textPrefix='British Broadcasting Corporation News'"
  ```
  (note that single quotes are used here because the prefix contains spaces) 
* Multiple options can be specified together;
  ```
  ./mvnw spring-boot:run -Dspring-boot.run.arguments="--casparCGServer.host=ccgs01.fwei.org.uk --leftTab.textPrefix='Aunty Beeb News'"
  ```
## Web UI
To use the control app, open your web browser and browse to;
* [http://localhost:8080/](http://localhost:8080/)
... to see some details of the app's internal state and a link to toggle the Left tab.