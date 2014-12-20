#Spring Batch Example
A collection of code from me learning Spring Batch (with annotations). I deliberately chose not to use spring boot, as I prefer to do my own configuration and setup when learning.

##Build and run:
```
gradle build
```

```
gradle execute
```

##Run from jar:
Builds a jar containing all dependencies
```
gradle fatJar
```

```
java -jar build/libs/spring-batch-example-all-1.0.0.jar com.example.batch.TransportImportBatch loadTransportJob
```

##Unit test
Includes jacoco test coverage report
```
gradle unitTest
```
Test reports will appear in the following dirs:
```
build/reports/tests/index.html
build/reports/coverage/index.html
```
____

##Scope Detail
###Implemented
* Annotation configuration
* Spring Hibernate Validation framework
* Custom field mappers for beans with Enums
* Run from Jar
* Unit tests + coverage

###Nice to have
* Integration testing with HSQL DB and Spring Profiles
