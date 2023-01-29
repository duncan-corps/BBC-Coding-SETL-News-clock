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
Among many other iYou should see output along the

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