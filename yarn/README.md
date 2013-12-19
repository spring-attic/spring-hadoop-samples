Spring Yarn Examples
====================

NOTE: These samples are currently configured to use Spring Hadoop 2.0.0.M4
release. You will get Hadoop dependencies through different maven artifacts
for Spring Hadoop. Currently vanilla Hadoop 2.2.0-alpha is supported.
```
# gradlew -Pdistro=hadoop20 clean build -x test
```

This project provides a number of examples to get you started using Spring Yarn and Spring Hadoop. These examples are designed to work with [Spring Hadoop] (http://www.springsource.org/spring-hadoop) 2.0 or higher and are organized into the following sub projects:

# Yarn Examples

* batch-amjob - Example of running Spring Batch job only on Application Master
* batch-files - Example of running Spring Batch partitioned job with HDFS file processing
* batch-partition - Example of running Spring Batch partitioned job
* custom-amservice - Example of using Spring Integration as custom Application Master Service
* kill-application - Example of killing an applicatoin
* list-applications - Example of listing applications known to resource manager
* multi-context - Example of running a spring application context on multiple containers
* simple-command - Example of running a command with containers
* restart-context - Example of custom failed container handling 

# Imporing Examples to IDE

This project is built with gradle and each example may be imported to your Java IDE. If you are using Eclipse or SpringSource Tool Suite, go to the directory where you downloaded this project and type:

        # gradlew eclipse

If you are using IDEA, 

        # gradlew idea

# Running Examples 

Detailed instructions for each example may be found in its own README file.
These README files has a section *Quick Instructions* which is handy
if you're already familiar with example and just want to copy/paste commands.

# General Prerequisites

Examples depends on libraries which are not on Hadoop classpath
on default. These dependencies are collected together during the
examples build process.

For example if project  *yarn-examples-simple-command* is built:
```
# gradlew :yarn-examples-common:yarn-examples-simple-command:build -x test
```
Project files are collected under *simple-command/build/libs* and dependencies
under *simple-command/build/dependency-libs*.

Every example jar file  has its own directory location in HDFS.

```
[root@centos hadoop]# hadoop/bin/hdfs dfs -ls /lib
/lib/aopalliance-1.0.jar
/lib/jackson-annotations-2.1.4.jar
/lib/jackson-core-2.1.4.jar
/lib/jackson-databind-2.1.4.jar
/lib/spring-aop-3.1.3.RELEASE.jar
/lib/spring-asm-3.1.3.RELEASE.jar
/lib/spring-batch-core-2.1.9.RELEASE.jar
/lib/spring-batch-infrastructure-2.1.9.RELEASE.jar
/lib/spring-beans-3.1.3.RELEASE.jar
/lib/spring-context-3.1.3.RELEASE.jar
/lib/spring-context-support-3.1.3.RELEASE.jar
/lib/spring-core-3.1.3.RELEASE.jar
/lib/spring-data-hadoop-2.0.0.M4-hadoop22.jar
/lib/spring-data-hadoop-core-2.0.0.M4-hadoop22.jar
/lib/spring-expression-3.1.3.RELEASE.jar
/lib/spring-integration-core-2.2.3.RELEASE.jar
/lib/spring-integration-ip-2.2.3.RELEASE.jar
/lib/spring-integration-stream-2.2.3.RELEASE.jar
/lib/spring-jdbc-3.1.3.RELEASE.jar
/lib/spring-retry-1.0.2.RELEASE.jar
/lib/spring-tx-3.1.3.RELEASE.jar
/lib/spring-yarn-batch-2.0.0.M4-hadoop22.jar
/lib/spring-yarn-core-2.0.0.M4-hadoop22.jar
/lib/spring-yarn-integration-2.0.0.M4-hadoop22.jar
/lib/yarn-examples-common-2.0.0.BUILD-SNAPSHOT.jar

[root@centos hadoop]# hadoop/bin/hdfs dfs -ls /app/simple-command
/app/simple-command/yarn-examples-simple-command-2.0.0.BUILD-SNAPSHOT-tests.jar
/app/simple-command/yarn-examples-simple-command-2.0.0.BUILD-SNAPSHOT.jar
...
```

JUnit tests are not dependant on a running cluster but you need to have
Hadoop installed on a host where tests are executed. Also familiar
enviroment variables shown below must exist. 
```
export PATH=$PATH:/usr/local/javas/java/bin
export JAVA_HOME=/usr/local/javas/java
export JRE_HOME=/usr/local/javas/java/jre
export HADOOP_HOME=/usr/local/hadoops/hadoop
export HADOOP_MAPRED_HOME=$HADOOP_HOME
export HADOOP_COMMON_HOME=$HADOOP_HOME
export HADOOP_HDFS_HOME=$HADOOP_HOME
export HADOOP_YARN_HOME=$HADOOP_HOME
export YARN_HOME=$HADOOP_HOME
export HADOOP_CONF_DIR=$HADOOP_HOME/conf
export YARN_CONF_DIR=$HADOOP_HOME/conf
```
