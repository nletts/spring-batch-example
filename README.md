#Spring Batch Example
A collection of code from me learning Spring Batch (with annotations)

##Build and Run:
```
gradle build

gradle execute
```

##Run from jar
Builds a jar containing all dependencies
```
gradle fatJar
```

```
java -jar build/libs/spring-batch-example-all-1.0.0.jar com.example.batch.TransportImportBatch loadTransportJob
```


##Scope Detail
###Implemented
* Annotation configuration
* Spring Hibernate Validation framework
* Run from Jar

###Unimplemented
* Unit tests
* Integration testing with HSQL DB
