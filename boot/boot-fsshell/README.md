# What you'll build

You'll build a simple Spring Hadoop application using Spring Boot
doing a simple listing from hdfs.

# Build

With gradle:
```
$ ./gradlew clean build
```

With maven:
```
$ mvn clean package
```

# Run the application

With gradle:
```
$ java -jar build/libs/boot-fsshell-0.1.0.jar
```

With maven:
```
$ java -jar target/boot-fsshell-0.1.0.jar
```

Running the command you should get a listing of files under `/tmp` hdfs directory.

```
> hdfs://localhost:8020/tmp
> hdfs://localhost:8020/tmp/hadoop-yarn
> hdfs://localhost:8020/tmp/hadoop-yarn/staging
> hdfs://localhost:8020/tmp/hadoop-yarn/staging/history
> hdfs://localhost:8020/tmp/hadoop-yarn/staging/history/done
> hdfs://localhost:8020/tmp/hadoop-yarn/staging/history/done_intermediate
```

# How it works

Spring Hadoop `spring-data-hadoop-boot` package contains Boot auto-configuration
for Hadoop. Currently it will automatically configure Hadoop's `Configuration` class
as a bean in Spring application context. `FsShell` is created automatically and
configured with automatically created `Configuration` bean. To use local
hdfs instance, simply place below content to `application.yml` file.


```
spring:
  main:
    show_banner: false
  hadoop:
    fsUri: hdfs://localhost:8020
```

