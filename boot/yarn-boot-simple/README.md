# What you'll build

You'll build a simple Hadoop YARN application with Spring Hadoop and Spring Boot.

# Build

Simple run a gradle build command.

```text
$ ./gradlew clean build
```

# Run the application

Now that you've successfully compiled and packaged your application, it's time to do the fun part and execute it on a Hadoop YARN.

Below listing shows files after a succesfull gradle build.

```text
$ ls -lt build/libs/
-rw-r--r-- 1 hadoop hadoop 35975001 Feb  2 17:39 yarn-boot-simple-container-0.1.0.jar
-rw-r--r-- 1 hadoop hadoop 35973937 Feb  2 17:39 yarn-boot-simple-client-0.1.0.jar
-rw-r--r-- 1 hadoop hadoop 35973840 Feb  2 17:39 yarn-boot-simple-appmaster-0.1.0.jar
```

Simply run your executable client jar.

```text
$ java -jar build/libs/yarn-boot-simple-client-0.1.0.jar
```

Using a Resource Manager UI you can see status of an application.

![Resource Manager UI](https://raw.github.com/spring-projects/spring-hadoop-samples/master/boot/yarn-boot-simple/rm-ui.png)

To find Hadoop's application logs, do a little find within a configured userlogs directory.

```text
$ find hadoop/logs/userlogs/|grep std
hadoop/logs/userlogs/application_1391506550167_0001/container_1391506550167_0001_01_000002/Container.stdout
hadoop/logs/userlogs/application_1391506550167_0001/container_1391506550167_0001_01_000002/Container.stderr
hadoop/logs/userlogs/application_1391506550167_0001/container_1391506550167_0001_01_000001/Appmaster.stdout
hadoop/logs/userlogs/application_1391506550167_0001/container_1391506550167_0001_01_000001/Appmaster.stderr
```

Grep logging output from a `HelloPojo` class.

```text
$ grep HelloPojo hadoop/logs/userlogs/application_1391506550167_0001/container_1391506550167_0001_01_000002/Container.stdout
[2014-02-02 17:40:38,314] boot - 11944  INFO [main] --- HelloPojo: Hello from HelloPojo
[2014-02-02 17:40:38,315] boot - 11944  INFO [main] --- HelloPojo: About to list from hdfs root content
[2014-02-02 17:40:41,134] boot - 11944  INFO [main] --- HelloPojo: FileStatus{path=hdfs://localhost:8020/; isDirectory=true; modification_time=1390823919636; access_time=0; owner=root; group=supergroup; permission=rwxr-xr-x; isSymlink=false}
[2014-02-02 17:40:41,135] boot - 11944  INFO [main] --- HelloPojo: FileStatus{path=hdfs://localhost:8020/app; isDirectory=true; modification_time=1391203430490; access_time=0; owner=jvalkealahti; group=supergroup; permission=rwxr-xr-x; isSymlink=false}
```

