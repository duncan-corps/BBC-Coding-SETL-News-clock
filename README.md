# BBC-Coding-SETL-News-clock
BBC SETL Coding test: Control and update News lower thirds clock.

## Setting up
You need Java 17.

## The control app
To run the control app, at the command line;
```./mvnw -DskipTests clean spring-boot:run```
This will use default values for;
* CasparCG Server host: localhost
* Left tab text prefix: BBC News

The CasparCG Server host can be specified like this;
```./mvnw -DskipTests clean install spring-boot:run -Dspring-boot.run.arguments="--casparCGServer.host=ccgs01.fwei.org.uk"```

The Left tab text prefix can be specified like this;
```./mvnw -DskipTests clean install spring-boot:run -Dspring-boot.run.arguments="--leftTab.textPrefix='British Broadcasting Corporation News'"```
Note that single quotes are used here because the prefix contains spaces) 

Both can be specified together;
```./mvnw -DskipTests clean install spring-boot:run -Dspring-boot.run.arguments="--casparCGServer.host=ccgs01.fwei.org.uk --leftTab.textPrefix='Aunty Beeb News'"```

To use the control app, open your web browser and browse to;
```http://localhost:8080/``` 
